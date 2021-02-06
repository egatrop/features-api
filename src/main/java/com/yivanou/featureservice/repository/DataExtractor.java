package com.yivanou.featureservice.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yivanou.featureservice.repository.entity.Feature;
import com.yivanou.featureservice.repository.entity.FeatureCollection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class DataExtractor {

    public Map<UUID, Feature> extract(String fileName) {
        final String jsonString = getJsonString(fileName);
        return deserializeJson(fileName, jsonString);
    }

    private String getJsonString(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            return new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Unable to fetch data from file {}", fileName);
            e.printStackTrace();
        }
        return "";
    }

    private Map<UUID, Feature> deserializeJson(String fileName, String jsonString) {
        try {
            return Stream.of(getObjectMapper().readValue(jsonString, FeatureCollection[].class))
                    .filter(fc -> fc.getFeatures() != null)
                    .flatMap(fc -> fc.getFeatures().stream())
                    .filter(this::isValid)
                    .collect(Collectors.toMap(
                            f -> f.getProperties().getId(),
                            Function.identity())
                    );
        } catch (JsonProcessingException e) {
            log.error("Unable to deserialize data from file {}", fileName);
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    private boolean isValid(Feature f) {
        return f.getProperties() != null
                && f.getProperties().getId() != null
                && StringUtils.hasLength(f.getProperties().getId().toString());
    }

    private ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
