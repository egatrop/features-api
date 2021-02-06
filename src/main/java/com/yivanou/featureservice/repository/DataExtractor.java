package com.yivanou.featureservice.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yivanou.featureservice.repository.entity.Feature;
import com.yivanou.featureservice.repository.entity.FeatureCollection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class DataExtractor {

    public Map<UUID, Feature> extract(String jsonString) {
        return extractInternal(jsonString);
    }

    private Map<UUID, Feature> extractInternal(String fileName) {
        try {
            final String jsonString = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(fileName).toURI())));
            return Stream.of(getObjectMapper().readValue(jsonString, FeatureCollection[].class))
                    .flatMap(fc -> fc.getFeatures().stream())
                    .collect(Collectors.toMap(f -> f.getProperties().getId(), Function.identity()));
        } catch (URISyntaxException | IOException e) {
            log.error("Unable to fetch data from file {}", fileName);
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    private ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
