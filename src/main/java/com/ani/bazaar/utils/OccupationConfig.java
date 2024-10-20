package com.ani.bazaar.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class OccupationConfig {

    @Value("${occupation.options}")
    private String occupationOptions;

    private final ObjectMapper objectMapper;

    public OccupationConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Map<String, String>> getOccupationOptions() throws Exception {
        return objectMapper.readValue(occupationOptions, new TypeReference<>() {});
    }
}
