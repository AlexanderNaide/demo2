package com.example.demo.api;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static com.example.demo.api.ApiClientConfigurationProperties.CONNECTION_TIMEOUT_MS;
//import static pro.adeo.supplier.storage.pi.globalFilters.configurations.ApiClientConfigurationProperties.CONNECTION_TIMEOUT_MS;

@Configuration
@EnableConfigurationProperties(ApiClientConfigurationProperties.class)
@RequiredArgsConstructor
public class AdeoApiConfiguration {

    private final ApiClientConfigurationProperties properties;

    @Bean(value = "adeo-api-graphql")
    public HttpGraphQlClient httpGraphQlClient(){
        WebClient webClient = WebClient.builder()
                .baseUrl(properties.getServiceUrl())
                .build();
        return HttpGraphQlClient.builder(webClient).build();
    }


    @Bean(value = "adeo-api-webclient")
    public WebClient productServiceWebClient(){

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECTION_TIMEOUT_MS)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(CONNECTION_TIMEOUT_MS))
                                .addHandlerLast(new WriteTimeoutHandler(CONNECTION_TIMEOUT_MS)));

        return WebClient.builder()
                .baseUrl(properties.getHost())
                .defaultHeader("User-Agent", "Mozilla/5.0 (compatible; YandexImageResizer/2.0)")
                .defaultHeader("Accept", "*/*")
                .defaultHeader("API-Key", properties.getApiKey())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

    }
}
