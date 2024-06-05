package com.uma.vulnesolve.controllers;

import com.uma.vulnesolve.models.vulnerabilidades.estadistica.Estadistica;
import com.uma.vulnesolve.models.vulnerabilidades.estadistica.TotalResults;
import com.uma.vulnesolve.models.vulnerabilidades.nve.JsonNve;
import com.uma.vulnesolve.models.vulnerabilidades.vulnesolve.JsonVulneSolve;
import com.uma.vulnesolve.services.NveApiService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nve")
public class NveApiController {

    private NveApiService nveApiService;

    public NveApiController(NveApiService nveApiService) {
        this.nveApiService = nveApiService;
    }

    @RequestMapping("/hola")
    public String hola() {
        return "Hola";
    }

    @RequestMapping("/vulnerabilities/{keywordSearch}")
    public JsonNve getVulnerabilities(@PathVariable String keywordSearch) {
        return nveApiService.getVulnerabilities(keywordSearch);
    }

    @RequestMapping("/vulnerabilidades/{keywordSearch}")
    public JsonVulneSolve getVulnerabilidades(@PathVariable String keywordSearch) {
        return nveApiService.getVulnerabilidades(keywordSearch);
    }

    @RequestMapping("/estadisticas/vulnerabilidades/{keywordSearch}")
    public TotalResults getEstadisticaVulnerabilidad(@PathVariable String keywordSearch) {
        return nveApiService.getEstadisticaVulnerabilidad(keywordSearch);
    }

    @RequestMapping("/estadisticas/severidad/v2")
    public List<Estadistica> getEstadisticaV2() {
        List<Estadistica> estadisticas = List.of(
                new Estadistica("LOW", nveApiService.getEstadisticaV2("LOW").getTotalResults()),
                new Estadistica("MEDIUM", nveApiService.getEstadisticaV2("MEDIUM").getTotalResults()),
                new Estadistica("HIGH", nveApiService.getEstadisticaV2("HIGH").getTotalResults())
        );

        return estadisticas;
    }

    @RequestMapping("/estadisticas/severidad/v3")
    public List<Estadistica> getEstadisticaV3() {
        List<Estadistica> estadisticas = List.of(
                new Estadistica("LOW", nveApiService.getEstadisticaV3("LOW").getTotalResults()),
                new Estadistica("MEDIUM", nveApiService.getEstadisticaV3("MEDIUM").getTotalResults()),
                new Estadistica("HIGH", nveApiService.getEstadisticaV3("HIGH").getTotalResults()),
                new Estadistica("CRITICAL", nveApiService.getEstadisticaV3("CRITICAL").getTotalResults())
        );

        return estadisticas;
    }

}
