package com.fih.ishareing.configurations.security;

import java.util.HashMap;
import java.util.Map;

import com.fih.ishareing.utils.signature.ApiSignature;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.signature")
public class ApiSignatureConfig {

    private Map<String, String> clients = new HashMap<>();

    public Map<String, String> getClients() {
        return clients;
    }

    @Bean
    public ApiSignature apiSignature() {
        ApiSignature apiSignature = new ApiSignature();
        apiSignature.setClients((getClients()));
        return apiSignature;
    }
}