package org.example.springbootinpractice.ch08;

import io.netty.channel.ChannelOption;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


public class ReactorClient {
    private final WebClient webClient;
    public ReactorClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .clientConnector(getClientConnector())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(30 * 1024 * 1024))
                .build())
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }
    public ReactorClientHttpConnector getClientConnector() {
        return new ReactorClientHttpConnector(HttpClient.create()
                .followRedirect(true)
                .compress(true)
                .secure()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000));
    }
    // HTTP 요청 로깅, HTTP 요청 메서드, URL, 모든 HTTP 요청헤더 출력
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers()
                    .forEach((key, values) -> System.out.println("Header: " + key + ": " + values));
            return Mono.just(clientRequest);
        });
    }
    // HTTP 응답로깅, HTTP응답 상태 코드와 모든 HTTP 응답헤더 출력
    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println("Response: " + clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders()
                    .forEach((key, values) -> System.out.println("Header: " + key + ": " + values));
            return Mono.just(clientResponse);
        });
    }
}
