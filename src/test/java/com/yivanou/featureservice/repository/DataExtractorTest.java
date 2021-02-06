package com.yivanou.featureservice.repository;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DataExtractorTest {

    private static final String JSON_FILE = "data/data.json";
    private final DataExtractor extractor = new DataExtractor();

    @Test
    public void shouldExtractFeatureData() {
        assertThat(extractor.extract(JSON_FILE)).isNotEmpty();
    }
}
