package com.example.demo.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api-client")
public class ApiClientConfigurationProperties {

    public static final int CONNECTION_TIMEOUT_MS = 5000;
    private String serviceUrl;
    private String host;
    private String apiKey;
}
