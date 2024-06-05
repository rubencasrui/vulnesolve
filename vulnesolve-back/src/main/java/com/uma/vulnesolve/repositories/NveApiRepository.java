package com.uma.vulnesolve.repositories;

import com.uma.vulnesolve.models.vulnerabilidades.estadistica.TotalResults;
import com.uma.vulnesolve.models.vulnerabilidades.nve.JsonNve;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Repository
public class NveApiRepository {

    private String urlBase;
    private String apiKey;
    private WebClient webClient;
    private int segundos;
    private int bytes;

    public NveApiRepository() {
        this.urlBase = "https://services.nvd.nist.gov/rest/json/cves/2.0";
        this.apiKey = "843a5258-e1ff-4526-990b-35e11e6d1f70";
        this.segundos = 30;
        this.bytes = 64 * 1024 * 1024; // 64 MB

        HttpClient httpClient = HttpClient.create().responseTimeout(java.time.Duration.ofSeconds(segundos));

        this.webClient = WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(bytes))
                .baseUrl(urlBase)
                .defaultHeader("apiKey",apiKey)
                .build();
    }

    public Mono<JsonNve> getVulnerabilidades(String keywordSearch) {
        Mono<JsonNve> respuesta = null;

        respuesta = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("keywordSearch",keywordSearch)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Error en la petición"))
                )
                .bodyToMono(JsonNve.class);

        return respuesta;
    }

    public Mono<TotalResults> getEstadisticaVulnerabilidad(String keywordSearch) {
        Mono<TotalResults> respuesta = null;

        respuesta = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("keywordSearch",keywordSearch)
                        .queryParam("resultsPerPage", "1")
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Error en la petición"))
                )
                .bodyToMono(TotalResults.class);

        return respuesta;
    }

    public Mono<TotalResults> getEstadisticaV2(String severity) {
        Mono<TotalResults> respuesta = null;

        respuesta = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("cvssV2Severity",severity)
                        .queryParam("resultsPerPage", "1")
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Error en la petición"))
                )
                .bodyToMono(TotalResults.class);

        return respuesta;
    }

    public Mono<TotalResults> getEstadisticaV3(String severity) {
        Mono<TotalResults> respuesta = null;

        respuesta = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("cvssV3Severity",severity)
                        .queryParam("resultsPerPage", "1")
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Error en la petición"))
                )
                .bodyToMono(TotalResults.class);

        return respuesta;
    }

}
