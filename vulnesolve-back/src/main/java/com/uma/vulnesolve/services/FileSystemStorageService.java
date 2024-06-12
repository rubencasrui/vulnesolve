package com.uma.vulnesolve.services;

import com.uma.vulnesolve.exceptions.StorageException;
import com.uma.vulnesolve.exceptions.StorageFileNotFoundException;
import com.uma.vulnesolve.models.nmap.EquipoNmap;
import com.uma.vulnesolve.models.nmap.EscaneoNmap;
import com.uma.vulnesolve.models.nmap.PuertoNmap;
import com.uma.vulnesolve.models.nmap.UnionNmap;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${media.location}")
    private String mediaLocation;
    private Path rootLocation;

    @Autowired
    private NveApiService nveApiService;

    @Override
    @PostConstruct
    public void iniciar() throws IOException {
        rootLocation = Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);
    }

    @Override
    public String guardar(MultipartFile file) {
        String nombreArchivo = (new Date()).getTime()+"_"+file.getOriginalFilename();

        try {
            if (file.isEmpty()) {
                throw new StorageException("Fallo al guardar archivo vacío.");
            }

            Path destinationFile = this.rootLocation
                    .resolve(Paths.get(nombreArchivo))
                    .normalize()
                    .toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("No se puede guardar el archivo fuera del directorio actual.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            nombreArchivo = null;
            throw new StorageException("Fallo al guardar el archivo.", e);
        }

        return nombreArchivo;
    }

    @Override
    public Stream<Path> cargarTodos() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Fallo al leer los ficheros almacenados. ", e);
        }

    }

    @Override
    public Path cargar(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource cargarComoRecurso(String filename) {
        try {
            Path file = cargar(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("No se ha podido leer el archivo: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("No se ha podido leer el archivo: " + filename, e);
        }
    }

    @Override
    public void borrar(String filename) {
        try {
            Path file = cargar(filename);
            Files.delete(file);
        }
        catch (IOException e) {
            throw new StorageException("No se ha podido borrar el archivo: " + filename, e);
        }
    }

    @Override
    public EscaneoNmap leerEscaneo(String filename) {
        // Crear el escaneo
        EscaneoNmap escaneoNmap = new EscaneoNmap();
        int idEquipos = -1;

        // Crear un diccionario de equipos
        Map<String, EquipoNmap> dicEquipos = new HashMap<>();

        // Añado el primero localhost
        EquipoNmap localhost = new EquipoNmap(++idEquipos, "localhost", "", 0, 0, 0, new ArrayList<>());
        dicEquipos.put(localhost.getIp(), localhost);

        try {
            // cargar el archivo XML
            File archivoXML = cargar(filename).toFile();

            // Crear un DocummentBuilderFactory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Crear un DocumentBuilder
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parsear el archivo XML y obtener un documento Document
            Document doc = dBuilder.parse(archivoXML);

            // Normalizar el documento
            doc.getDocumentElement().normalize();

            // Obtener raíz del documento
            Element rootElement = doc.getDocumentElement();

            // Obtener la lista de nodos 'host'
            NodeList nodeList = rootElement.getElementsByTagName("host");

            // Iterar sobre la lista de nodos 'host'
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element hostElement = (Element) nodeList.item(i);
                Element statusElement = (Element) hostElement.getElementsByTagName("status").item(0);
                String state = statusElement.getAttribute("state");
                if (state.equals("up")) {
                    // Crear el equipo
                    EquipoNmap equipoNmap = new EquipoNmap();
                    equipoNmap.setId(++idEquipos);
                    equipoNmap.setX(idEquipos*10);
                    equipoNmap.setY(idEquipos*10);

                    // Obtener la lista de nodos 'address' dentro de 'host'
                    NodeList addressList = hostElement.getElementsByTagName("address");
                    for (int j = 0; j < addressList.getLength(); j++) {
                        Element addressElement = (Element) addressList.item(j);

                        String addr = addressElement.getAttribute("addr");
                        String type = addressElement.getAttribute("addrtype");
                        String vendor = addressElement.getAttribute("vendor");

                        if (type.equals("ipv4")) {
                            equipoNmap.setIp(addr);
                        }
                        else if (type.equals("mac")) {
                            equipoNmap.setMac(addr);
                        }
                    }

                    // Una vez tiene la ip lo añado al diccionario
                    dicEquipos.put(equipoNmap.getIp(), equipoNmap);

                    // Crear lista de puertos del equipo
                    List<PuertoNmap> puertoNmaps = new ArrayList<>();
                    equipoNmap.setPuertos(puertoNmaps);

                    // Obtener la lista de nodos 'ports' dentro de 'host'
                    NodeList portsList = hostElement.getElementsByTagName("ports");
                    for (int j = 0; j < portsList.getLength(); j++) {
                        Element portsElement = (Element) portsList.item(j);
                        // Obtener la lista de nodos 'port' dentro de 'ports'
                        NodeList portList = portsElement.getElementsByTagName("port");
                        for (int k = 0; k < portList.getLength(); k++) {
                            // Creo este puerto
                            PuertoNmap puertoNmap = new PuertoNmap();
                            // Lo añado a la lista de puertos del equipo
                            puertoNmaps.add(puertoNmap);

                            Element portElement = (Element) portList.item(k);

                            String portId = portElement.getAttribute("portid");
                            String protocol = portElement.getAttribute("protocol");

                            Element stateElement = (Element) portElement.getElementsByTagName("state").item(0);

                            String statePort = stateElement.getAttribute("state");
                            String reason = stateElement.getAttribute("reason");

                            Element serviceElement = (Element) portElement.getElementsByTagName("service").item(0);

                            String name = serviceElement.getAttribute("name");
                            String product = serviceElement.getAttribute("product");

                            // Añadir el numero de puerto y nombre del servicio
                            puertoNmap.setNumero(Integer.parseInt(portId));
                            puertoNmap.setEstado(statePort);
                            puertoNmap.setNombre(name);
                            puertoNmap.setDescripcion(product);
                            puertoNmap.setVulnerabilidades(null);
                        }
                    }

                    // Asignar tipo segun la cantidad de puertos abiertos
                    if(puertoNmaps.size() == 0) {
                        equipoNmap.setTipo(0);
                    }
                    else if(1 <= puertoNmaps.size() && puertoNmaps.size() <= 2){
                        equipoNmap.setTipo(1);
                    }
                    else if(3 <= puertoNmaps.size() && puertoNmaps.size() <= 4){
                        equipoNmap.setTipo(2);
                    }
                    else if(4 <= puertoNmaps.size() && puertoNmaps.size() <= 8){
                        equipoNmap.setTipo(3);
                    }
                    else {
                        equipoNmap.setTipo(4);
                    }

                    // Obtener la lista de nodos 'trace' dentro de 'host'
                    NodeList traceList = hostElement.getElementsByTagName("trace");
                    if(traceList.getLength() == 0) {
                        Element addressElement = (Element) addressList.item(0);

                        UnionNmap unionNmap = new UnionNmap();

                        EquipoNmap origen = dicEquipos.get("localhost");
                        EquipoNmap destino = dicEquipos.get(addressElement.getAttribute("addr"));

                        unionNmap.setSource(origen.getId());
                        unionNmap.setTarget(destino.getId());

                        escaneoNmap.getUniones().add(unionNmap);
                    }
                    else {
                        for (int j = 0; j < traceList.getLength(); j++) {
                            Element traceElement = (Element) traceList.item(j);

                            // Obtener la lista de nodos 'hop' dentro de 'trace'
                            NodeList hopList = traceElement.getElementsByTagName("hop");

                            if (hopList.getLength() == 0) {
                                Element addressElement = (Element) addressList.item(0);

                                UnionNmap unionNmap = new UnionNmap();

                                EquipoNmap origen = dicEquipos.get("localhost");
                                EquipoNmap destino = dicEquipos.get(addressElement.getAttribute("addr"));

                                unionNmap.setSource(origen.getId());
                                unionNmap.setTarget(destino.getId());

                                escaneoNmap.getUniones().add(unionNmap);
                            } else if (hopList.getLength() == 1) {
                                Element hopElement = (Element) hopList.item(0);

                                UnionNmap unionNmap = new UnionNmap();

                                EquipoNmap origen = dicEquipos.get("localhost");
                                EquipoNmap destino = dicEquipos.get(hopElement.getAttribute("ipaddr"));

                                unionNmap.setSource(origen.getId());
                                unionNmap.setTarget(destino.getId());

                                escaneoNmap.getUniones().add(unionNmap);
                            } else {
                                Element hopElementAnterior = null;
                                for (int k = 0; k < hopList.getLength(); k++) {
                                    Element hopElementActual = (Element) hopList.item(k);
                                    String ipActual = hopElementActual.getAttribute("ipaddr").toString();

                                    // Si no existe este equipo, lo añado
                                    if (!dicEquipos.containsKey(ipActual)) {
                                        EquipoNmap nuevoEquipoNmap = new EquipoNmap(++idEquipos, ipActual, "", idEquipos*10, idEquipos*10, 0, new ArrayList<>());
                                        dicEquipos.put(nuevoEquipoNmap.getIp(), nuevoEquipoNmap);
                                    }

                                    UnionNmap unionNmap = new UnionNmap();

                                    EquipoNmap origen = dicEquipos.get(k == 0 ? "localhost" : hopElementAnterior.getAttribute("ipaddr"));
                                    EquipoNmap destino = dicEquipos.get(hopElementActual.getAttribute("ipaddr"));

                                    unionNmap.setSource(origen.getId());
                                    unionNmap.setTarget(destino.getId());

                                    escaneoNmap.getUniones().add(unionNmap);

                                    hopElementAnterior = hopElementActual;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            throw new StorageException("Error en la lectura del archivo: " + filename, e);
        }

        // Añadir los equipos al escaneo
        escaneoNmap.setEquipos(new ArrayList<>(dicEquipos.values()));

        // Mostrar escaneo
        // System.out.println(escaneo);

        return escaneoNmap;
    }
}
