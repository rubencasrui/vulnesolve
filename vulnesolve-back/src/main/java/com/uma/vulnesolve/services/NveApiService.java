package com.uma.vulnesolve.services;

import com.uma.vulnesolve.models.vulnerabilidades.estadistica.TotalResults;
import com.uma.vulnesolve.models.vulnerabilidades.nve.*;
import com.uma.vulnesolve.models.vulnerabilidades.vulnesolve.JsonVulneSolve;
import com.uma.vulnesolve.models.vulnerabilidades.vulnesolve.Severidad;
import com.uma.vulnesolve.models.vulnerabilidades.vulnesolve.Vulnerabilidad;
import com.uma.vulnesolve.repositories.NveApiRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NveApiService {

    public static final double INCREMENTO_POR_ACTUALIDAD = 0.2;
    private NveApiRepository nveApiRepository;

    public NveApiService(NveApiRepository nveApiRepository) {
        this.nveApiRepository = nveApiRepository;
    }

    public JsonNve getVulnerabilities(String keywordSearch) {
        return nveApiRepository.getVulnerabilidades(keywordSearch).block();
    }

    public JsonVulneSolve getVulnerabilidadesOld(String keywordSearch) {
        JsonNve peticion = nveApiRepository.getVulnerabilidades(keywordSearch).block();

        JsonVulneSolve respuesta = new JsonVulneSolve();

        respuesta = transformar(peticion, respuesta);
        respuesta.setNombre(keywordSearch);

        return respuesta;
    }

    public JsonVulneSolve getVulnerabilidades(String keywordSearch) {
        long inicio = 0;
        long fin = 0;
        double tiempoTotalResults = 0;
        double tiempoPeticion = 0;
        double tiempoTransformar = 0;

        inicio = (new Date()).getTime();
        int totalResultados = nveApiRepository.getEstadisticaVulnerabilidad(keywordSearch).block().getTotalResults();
        fin = (new Date()).getTime();
        tiempoTotalResults = (double) (fin - inicio) /1000;

        JsonNve peticion = new JsonNve();
        JsonVulneSolve respuesta = new JsonVulneSolve();
        int resultPerPage = 2000;


        inicio = (new Date()).getTime();
        if (totalResultados > 0){
            if (totalResultados > resultPerPage) {
                int opcion = 1;
                if (opcion == 1){
                    peticion = nveApiRepository.getVulnerabilidadesParam(keywordSearch, resultPerPage+"", (totalResultados-resultPerPage)+"").block();
                }
                else if(opcion == 2){
                    peticion = nveApiRepository.getVulnerabilidadesParam(keywordSearch, resultPerPage+"", 0+"").block();
                    System.out.println("Cantidad de vulnerabilidades: " + peticion.getVulnerabilities().size());
                    while(peticion.getVulnerabilities().size()<totalResultados){
                        peticion.getVulnerabilities().addAll(
                                nveApiRepository.getVulnerabilidadesParam(keywordSearch, resultPerPage+"", peticion.getVulnerabilities().size()+"").block().getVulnerabilities()
                        );
                        System.out.println("Cantidad de vulnerabilidades: " + peticion.getVulnerabilities().size());
                    }
                }
            }
            else {
                peticion = nveApiRepository.getVulnerabilidades(keywordSearch).block();
            }
        }
        fin = (new Date()).getTime();
        tiempoPeticion = (double) (fin - inicio) /1000;

        respuesta.setNombre(keywordSearch);

        inicio = (new Date()).getTime();
        respuesta = transformar(peticion, respuesta);
        fin = (new Date()).getTime();
        tiempoTransformar = (double) (fin - inicio) /1000;

        System.out.println(keywordSearch + ": " + "tiempoTotalResults: " + tiempoTotalResults + ", tiempoPeticion: " + tiempoPeticion + ", tiempoTransformar: " + tiempoTransformar + ", tiempoTotal: " + (tiempoTotalResults + tiempoPeticion + tiempoTransformar));

        return respuesta;
    }

    public TotalResults getEstadisticaVulnerabilidad(String keywordSearch) {
        return nveApiRepository.getEstadisticaVulnerabilidad(keywordSearch).block();
    }

    public TotalResults getEstadisticaV2(String severity) {
        return nveApiRepository.getEstadisticaV2(severity).block();
    }

    public TotalResults getEstadisticaV3(String severity) {
        return nveApiRepository.getEstadisticaV3(severity).block();
    }

    private JsonVulneSolve transformar(JsonNve peticion, JsonVulneSolve respuesta) {

        respuesta.setResultados(peticion.getTotalResults());

        double puntuacion20 = 0;
        double puntuacion30 = 0;
        double puntuacion31 = 0;
        int cantidad20 = 0;
        int cantidad30 = 0;
        int cantidad31 = 0;
        int cantidadActuales = 0;

        int cantidadVulnerabilidades = peticion.getVulnerabilities().size();
        for(int i = 0; i < cantidadVulnerabilidades; i++){
            Vulnerabilities vulne = peticion.getVulnerabilities().get(i);
            Vulnerabilidad vulnerabilidad = new Vulnerabilidad();

            if (cantidadVulnerabilidades - i <= 2000){
            }
            respuesta.getVulnerabilidades().add(vulnerabilidad);

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
                }
            }

        }

        double puntuacion = 0;
        if (cantidad20 + cantidad30 + cantidad31 > 0) {
            puntuacion += (puntuacion20 + puntuacion30 + puntuacion31 ) / (cantidad20 + cantidad30 + cantidad31);
        }


        if (respuesta.getVulnerabilidades().size()>0) {
            puntuacion = puntuacion * (1 + ((double) cantidadActuales / respuesta.getVulnerabilidades().size()) * INCREMENTO_POR_ACTUALIDAD);
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
