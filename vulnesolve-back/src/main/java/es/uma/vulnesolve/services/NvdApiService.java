package es.uma.vulnesolve.services;

import es.uma.vulnesolve.models.dto.ConfiguracionApi;
import es.uma.vulnesolve.models.vulnerabilidades.estadistica.TotalResults;
import es.uma.vulnesolve.models.vulnerabilidades.vulnesolve.JsonVulneSolve;
import es.uma.vulnesolve.models.vulnerabilidades.vulnesolve.Severidad;
import es.uma.vulnesolve.models.vulnerabilidades.vulnesolve.Vulnerabilidad;
import es.uma.vulnesolve.repositories.NvdApiRepository;
import es.uma.vulnesolve.models.vulnerabilidades.nvd.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NvdApiService {

    private NvdApiRepository nvdApiRepository;

    private ConfiguracionApiService configuracionApiService;

    public NvdApiService(NvdApiRepository nvdApiRepository, ConfiguracionApiService configuracionApiService) {
        this.nvdApiRepository = nvdApiRepository;

        this.configuracionApiService = configuracionApiService;
    }

    public JsonNvd getVulnerabilities(String keywordSearch) {
        return nvdApiRepository.getVulnerabilidades(keywordSearch).block();
    }

    private ConfiguracionApi obtenerConfiguracionApi(String nombre) {
        Optional<ConfiguracionApi> configuracionApi = configuracionApiService.buscarPorNombre(nombre);
        ConfiguracionApi config = new ConfiguracionApi(0L, "PREDETERMINADA", 10, 2, false, 20);

        if (configuracionApi.isPresent()) {
            config = configuracionApi.get();
        }

        return config;
    }

    public JsonVulneSolve getVulnerabilidadesIndividuales(String keywordSearch) {
        return getVulnerabilidades(keywordSearch, obtenerConfiguracionApi("NVD-INDIVIDUAL"));
    }

    public JsonVulneSolve getVulnerabilidadesMultiple(String keywordSearch) {
        return getVulnerabilidades(keywordSearch, obtenerConfiguracionApi("NVD-MULTIPLE"));
    }

    public JsonVulneSolve getVulnerabilidades(String keywordSearch, ConfiguracionApi configuracionApi) {
        long inicio = 0;
        long fin = 0;
        double tiempoTotalResults = 0;
        double tiempoPeticion = 0;
        double tiempoTransformar = 0;

        JsonNvd peticion = new JsonNvd();
        JsonVulneSolve respuesta = new JsonVulneSolve();

        int resultPerPage = configuracionApi.getCantidadResultados();
        int modo = configuracionApi.getModoBusqueda();

        if (modo == 1) {
            // Modo 1: Se obtienen todos los resultados mas antiguos. De indice 0 a resultPerPage
            System.out.println("Modo 1");

            inicio = (new Date()).getTime();
            peticion = nvdApiRepository.getVulnerabilidadesParam(keywordSearch, resultPerPage+"", 0+"").block();
            fin = (new Date()).getTime();

            tiempoPeticion = (double) (fin - inicio) /1000;
        }
        else if (modo == 2) {
            // Modo 2: Se obtienen todos los resultados mas recientes. De indice totalResults-resultPerPage a totalResults
            System.out.println("Modo 2");

            // Se calcula la cantidad de vulnerabilidades
            inicio = (new Date()).getTime();
            int totalResultados = nvdApiRepository.getEstadisticaVulnerabilidad(keywordSearch).block().getTotalResults();
            fin = (new Date()).getTime();
            tiempoTotalResults = (double) (fin - inicio) /1000;

            // Se obtienen las vulnerabilidades si hay alguna
            inicio = (new Date()).getTime();
            if (totalResultados > 0){
                if (totalResultados > resultPerPage) {
                    // Si hay más vulnerabilidades que resultados a mostrar, se calcula el indice por el que empezar.
                    peticion = nvdApiRepository.getVulnerabilidadesParam(keywordSearch, resultPerPage+"", (totalResultados-resultPerPage)+"").block();
                }
                else {
                    // Si hay menos vulnerabilidades que resultados a mostrar, se obtienen todas las vulnerabilidades.
                    peticion = nvdApiRepository.getVulnerabilidadesParam(keywordSearch, totalResultados+"", 0+"").block();
                }
            }
            fin = (new Date()).getTime();
            tiempoPeticion = (double) (fin - inicio) /1000;
        }
        else if (modo == 3) {
            // Modo 3: Se obtienen todos los resultados
            System.out.println("Modo 3");

            // Se obtienen todas las vulnerabilidades hasta la cantidad asignada a resultPerPage
            inicio = (new Date()).getTime();
            peticion = nvdApiRepository.getVulnerabilidadesParam(keywordSearch, resultPerPage+"", 0+"").block();
            // Se calcula la cantidad de vulnerabilidades
            int totalResultados = peticion.getTotalResults();
            fin = (new Date()).getTime();

            tiempoTotalResults = (double) (fin - inicio) /1000;

            // Iterar hasta obtener todas las vulnerabilidades
            inicio = (new Date()).getTime();
            if (totalResultados > resultPerPage) {
                resultPerPage = 2000;
            }
            while(peticion.getVulnerabilities().size()<totalResultados){
                peticion.getVulnerabilities().addAll(
                    nvdApiRepository.getVulnerabilidadesParam(keywordSearch, resultPerPage+"", peticion.getVulnerabilities().size()+"").block().getVulnerabilities()
                );
            }

            fin = (new Date()).getTime();
            tiempoPeticion = (double) (fin - inicio) /1000;
        }
        else {
            System.out.println("Modo " + modo + " de búsqueda no válido");
        }

        respuesta.setNombre(keywordSearch);

        inicio = (new Date()).getTime();
        respuesta = transformar(peticion, respuesta, configuracionApi);
        fin = (new Date()).getTime();
        tiempoTransformar = (double) (fin - inicio) /1000;

        System.out.println(keywordSearch + ": " + "tiempoTotalResults: " + tiempoTotalResults + ", tiempoPeticion: " + tiempoPeticion + ", tiempoTransformar: " + tiempoTransformar + ", tiempoTotal: " + (tiempoTotalResults + tiempoPeticion + tiempoTransformar));

        return respuesta;
    }

    public TotalResults getEstadisticaVulnerabilidad(String keywordSearch) {
        return nvdApiRepository.getEstadisticaVulnerabilidad(keywordSearch).block();
    }

    public TotalResults getEstadisticaV2(String severity) {
        return nvdApiRepository.getEstadisticaV2(severity).block();
    }

    public TotalResults getEstadisticaV3(String severity) {
        return nvdApiRepository.getEstadisticaV3(severity).block();
    }

    private JsonVulneSolve transformar(JsonNvd peticion, JsonVulneSolve respuesta, ConfiguracionApi configuracionApi) {

        respuesta.setResultados(peticion.getTotalResults());

        double puntuacion20 = 0;
        double puntuacion30 = 0;
        double puntuacion31 = 0;
        int cantidad20 = 0;
        int cantidad30 = 0;
        int cantidad31 = 0;
        int cantidadActuales = 0;
        boolean soloCriticos = configuracionApi.isSoloCriticos();

        int cantidadVulnerabilidades = peticion.getVulnerabilities().size();
        for(int i = 0; i < cantidadVulnerabilidades; i++){
            Vulnerabilities vulne = peticion.getVulnerabilities().get(i);
            Vulnerabilidad vulnerabilidad = new Vulnerabilidad();

            Cve cve = vulne.getCve();
            vulnerabilidad.setId(vulne.getCve().getId());

            int prioridadEncontrado = 1;
            List<Description> descripcions = vulne.getCve().getDescriptions();
            for (int j = 0; j<descripcions.size() && prioridadEncontrado != 3; j++) {
                if (descripcions.get(j).getLang().equals("es")) {
                    vulnerabilidad.setDescripcion(descripcions.get(j).getValue());
                    prioridadEncontrado = 3;
                }
                else if (descripcions.get(j).getLang().equals("en")) {
                    vulnerabilidad.setDescripcion(descripcions.get(j).getValue());
                    prioridadEncontrado = 2;
                }
                else if (prioridadEncontrado != 2){
                    vulnerabilidad.setDescripcion(descripcions.get(j).getValue());
                }
            }

            int anyos =  (new Date()).getYear() - cve.getPublished().getYear();
            int ponderacion = (anyos > 5) ? 1 : (anyos > 3) ? 2 : 5;

            if (ponderacion>1){
                cantidadActuales++;
            }

            boolean esCritico = false;

            Metrics metrics = vulne.getCve().getMetrics();
            List<CvssMetricV20> cvssMetricV2 = metrics.getCvssMetricV2();
            if (cvssMetricV2 != null && !cvssMetricV2.isEmpty()) {
                for (CvssMetricV20 cvssMetric : cvssMetricV2) {
                    Severidad severidad = new Severidad();
                    vulnerabilidad.getSeveridades().add(severidad);

                    severidad.setFuente(cvssMetric.getSource());
                    severidad.setVersion("2.0");
                    severidad.setSeverity(cvssMetric.getBaseSeverity());
                    severidad.setValor(cvssMetric.getCvssData().getBaseScore());

                    puntuacion20 += ponderacion * severidad.getValor();
                    cantidad20 += ponderacion;
                }
            }
            List<CvssMetricV3X> cvssMetricV30 = metrics.getCvssMetricV30();
            if (cvssMetricV30 != null && !cvssMetricV30.isEmpty()) {
                for (CvssMetricV3X cvssMetric : cvssMetricV30) {
                    Severidad severidad = new Severidad();
                    vulnerabilidad.getSeveridades().add(severidad);

                    severidad.setFuente(cvssMetric.getSource());
                    severidad.setVersion("3.0");
                    severidad.setSeverity(cvssMetric.getCvssData().getBaseSeverity());
                    severidad.setValor(cvssMetric.getCvssData().getBaseScore());

                    puntuacion30 += ponderacion * severidad.getValor();
                    cantidad30 += ponderacion;

                    if (severidad.getSeverity().equals("CRITICAL")) {
                        esCritico = true;
                    }
                }
            }
            List<CvssMetricV3X> cvssMetricV31 = metrics.getCvssMetricV31();
            if (cvssMetricV31 != null && !cvssMetricV31.isEmpty()) {
                for (CvssMetricV3X cvssMetric : cvssMetricV31) {
                    Severidad severidad = new Severidad();
                    vulnerabilidad.getSeveridades().add(severidad);

                    severidad.setFuente(cvssMetric.getSource());
                    severidad.setVersion("3.1");
                    severidad.setSeverity(cvssMetric.getCvssData().getBaseSeverity());
                    severidad.setValor(cvssMetric.getCvssData().getBaseScore());

                    puntuacion31 += ponderacion * severidad.getValor();
                    cantidad31 += ponderacion;

                    if (severidad.getSeverity().equals("CRITICAL")) {
                        esCritico = true;
                    }
                }
            }

            if (soloCriticos){
                if (esCritico){
                    respuesta.getVulnerabilidades().add(vulnerabilidad);
                }
            }
            else {
                respuesta.getVulnerabilidades().add(vulnerabilidad);

            }

        }

        double puntuacion = 0;
        if (cantidad20 + cantidad30 + cantidad31 > 0) {
            puntuacion += (puntuacion20 + puntuacion30 + puntuacion31 ) / (cantidad20 + cantidad30 + cantidad31);
        }

        double incrementoIndice = configuracionApi.getIncrementoIndice()/100.0;

        if (respuesta.getVulnerabilidades().size()>0) {
            puntuacion = puntuacion * (1 + ((double) cantidadActuales / respuesta.getVulnerabilidades().size()) * incrementoIndice);
            if (puntuacion > 10)
                puntuacion = 10;
        }

        respuesta.setIndiceVulneSolve(puntuacion);
        if (0 <= puntuacion && puntuacion < 4) {
            respuesta.setSeveridadVulneSolve("BAJO");
        }
        else if (4 <= puntuacion && puntuacion < 7) {
            respuesta.setSeveridadVulneSolve("MEDIO");
        }
        else if (7 <= puntuacion && puntuacion < 9) {
            respuesta.setSeveridadVulneSolve("ALTO");
        }
        else {
            respuesta.setSeveridadVulneSolve("CRITICO");
        }
        return respuesta;
    }
}
