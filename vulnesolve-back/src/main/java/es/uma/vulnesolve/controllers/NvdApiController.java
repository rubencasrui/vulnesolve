package es.uma.vulnesolve.controllers;

import es.uma.vulnesolve.models.vulnerabilidades.estadistica.Estadistica;
import es.uma.vulnesolve.models.vulnerabilidades.estadistica.TotalResults;
import es.uma.vulnesolve.models.vulnerabilidades.nvd.JsonNvd;
import es.uma.vulnesolve.models.vulnerabilidades.vulnesolve.JsonVulneSolve;
import es.uma.vulnesolve.services.NvdApiService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nvd")
public class NvdApiController {

    private NvdApiService nvdApiService;

    public NvdApiController(NvdApiService nvdApiService) {
        this.nvdApiService = nvdApiService;
    }

    @RequestMapping("/vulnerabilities/{keywordSearch}")
    public JsonNvd getVulnerabilities(@PathVariable String keywordSearch) {
        return nvdApiService.getVulnerabilities(keywordSearch);
    }

    @RequestMapping("/vulnerabilidades/individual/{keywordSearch}")
    public JsonVulneSolve getVulnerabilidadesIndviduales(@PathVariable String keywordSearch) {
        return nvdApiService.getVulnerabilidadesIndividuales(keywordSearch);
    }

    @RequestMapping("/vulnerabilidades/multiple/{keywordSearch}")
    public JsonVulneSolve getVulnerabilidadesMultiples(@PathVariable String keywordSearch) {
        return nvdApiService.getVulnerabilidadesMultiple(keywordSearch);
    }

    @RequestMapping("/estadisticas/vulnerabilidades/{keywordSearch}")
    public TotalResults getEstadisticaVulnerabilidad(@PathVariable String keywordSearch) {
        return nvdApiService.getEstadisticaVulnerabilidad(keywordSearch);
    }

    @RequestMapping("/estadisticas/severidad/v2")
    public List<Estadistica> getEstadisticaV2() {
        List<Estadistica> estadisticas = List.of(
                new Estadistica("LOW", nvdApiService.getEstadisticaV2("LOW").getTotalResults()),
                new Estadistica("MEDIUM", nvdApiService.getEstadisticaV2("MEDIUM").getTotalResults()),
                new Estadistica("HIGH", nvdApiService.getEstadisticaV2("HIGH").getTotalResults())
        );

        return estadisticas;
    }

    @RequestMapping("/estadisticas/severidad/v3")
    public List<Estadistica> getEstadisticaV3() {
        List<Estadistica> estadisticas = List.of(
                new Estadistica("LOW", nvdApiService.getEstadisticaV3("LOW").getTotalResults()),
                new Estadistica("MEDIUM", nvdApiService.getEstadisticaV3("MEDIUM").getTotalResults()),
                new Estadistica("HIGH", nvdApiService.getEstadisticaV3("HIGH").getTotalResults()),
                new Estadistica("CRITICAL", nvdApiService.getEstadisticaV3("CRITICAL").getTotalResults())
        );

        return estadisticas;
    }

}
