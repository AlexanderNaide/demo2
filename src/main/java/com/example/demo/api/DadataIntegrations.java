package com.example.demo.api;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Component
public class DadataIntegrations {

    @Value("${dadata.headers.token}")
    private String token;

    @Bean
    public WebClient productServiceWebClient(){

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
//                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(5000))
                                .addHandlerLast(new WriteTimeoutHandler(1000)));

        return WebClient.builder()
                .baseUrl("https://suggestions.dadata.ru/suggestions/api/4_1/rs/iplocate/address")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Token " + token)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        // Это для нового спринга по докам

    }

}
