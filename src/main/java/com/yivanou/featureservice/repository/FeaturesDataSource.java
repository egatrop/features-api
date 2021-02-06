package com.yivanou.featureservice.repository;

import com.yivanou.featureservice.repository.entity.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeaturesDataSource {

    private static final String DATA_SRC = "data/data.json";

    @Autowired
    private DataExtractor extractor;

    private final Map<UUID, Feature> featuresToMap = new HashMap<>();

    @PostConstruct
    private void init() {
        log.info("Initializing data...");
        featuresToMap.putAll(extractor.extract(DATA_SRC));
    }

    public Optional<Feature> findById(UUID id) {
        return Optional.ofNullable(featuresToMap.get(id));
    }

    public List<Feature> getAll() {
        return new ArrayList<>(featuresToMap.values());
    }

    private Map<UUID, Feature> getFeaturesMap() {
        return new HashMap<>(featuresToMap);
    }
}
