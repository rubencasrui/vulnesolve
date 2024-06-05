package com.uma.vulnesolve.services;

import com.uma.vulnesolve.models.vulnerabilidades.estadistica.TotalResults;
import com.uma.vulnesolve.models.vulnerabilidades.nve.*;
import com.uma.vulnesolve.models.vulnerabilidades.vulnesolve.JsonVulneSolve;
import com.uma.vulnesolve.models.vulnerabilidades.vulnesolve.Severidad;
import com.uma.vulnesolve.models.vulnerabilidades.vulnesolve.Vulnerabilidad;
import com.uma.vulnesolve.repositories.NveApiRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NveApiService {

    private NveApiRepository nveApiRepository;

    public NveApiService(NveApiRepository nveApiRepository) {
        this.nveApiRepository = nveApiRepository;
    }

    public JsonNve getVulnerabilities(String keywordSearch) {
        return nveApiRepository.getVulnerabilidades(keywordSearch).block();
    }

    public JsonVulneSolve getVulnerabilidades(String keywordSearch) {
        JsonNve peticion = nveApiRepository.getVulnerabilidades(keywordSearch).block();

        JsonVulneSolve respuesta = new JsonVulneSolve();

        respuesta = transformar(peticion, respuesta);
        respuesta.setNombre(keywordSearch);

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

        int cantidadVulnerabilidades = peticion.getVulnerabilities().size();
        for(int i = 0; i < cantidadVulnerabilidades; i++){
            Vulnerabilities vulne = peticion.getVulnerabilities().get(i);
            Vulnerabilidad vulnerabilidad = new Vulnerabilidad();

            if (cantidadVulnerabilidades - i <= 100){
                respuesta.getVulnerabilidades().add(vulnerabilidad);
            }

            Cve cve = vulne.getCve();
            vulnerabilidad.setId(vulne.getCve().getId());

            int encontrado = 1;
            List<Description> descripcions = vulne.getCve().getDescriptions();
            for (int j = 0; j<descripcions.size() && encontrado != 3; j++) {
                if (descripcions.get(j).getLang().equals("es")) {
                    vulnerabilidad.setDescripcion(descripcions.get(j).getValue());
                    encontrado = 3;
                }
                else if (descripcions.get(j).getLang().equals("en")) {
                    vulnerabilidad.setDescripcion(descripcions.get(j).getValue());
                    encontrado = 2;
                }
                else if (encontrado != 2){
                    vulnerabilidad.setDescripcion(descripcions.get(j).getValue());
                }
            }

            int anyos =  (new Date()).getYear() - cve.getPublished().getYear();
            int ponderacion = (anyos > 5) ? 1 : (anyos > 3) ? 2 : 5;

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
