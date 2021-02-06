package com.yivanou.featureservice.repository;

import com.yivanou.featureservice.repository.entity.Feature;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DataExtractorTest {

    private static final String EMPTY_JSON = "data/empty.json";
    private static final String MISSING_ID_JSON = "data/missing_id.json";
    private static final String ONE_VALID_JSON = "data/one_valid_feature.json";

    private final DataExtractor extractor = new DataExtractor();

    @Test
    public void givenEmptyJson_whenExtract_thenReturnEmptyMap() {
        assertThat(extractor.extract(EMPTY_JSON)).isEmpty();
    }

    @Test
    public void givenJsonWithEmptyId_whenExtract_thenReturnEmptyMap() {
        assertThat(extractor.extract(MISSING_ID_JSON)).isEmpty();
    }

    @Test
    public void givenJsonWithEmptyIdForOneFeature_whenExtract_thenReturnMapWithoutThisFeature() {
        final Map<UUID, Feature> result = extractor.extract(ONE_VALID_JSON);

        assertThat(result).hasSize(1);

        final Feature any = result.values().stream().findAny().get();

        assertThat(any.getProperties()).isNotNull();
        assertThat(any.getProperties().getId()).isNotNull();
        assertThat(any.getProperties().getTimestamp()).isNotNull();
        assertThat(any.getProperties().getAcquisition()).isNotNull();
        assertThat(any.getProperties().getAcquisition().getBeginViewingDate()).isNotNull();
        assertThat(any.getProperties().getAcquisition().getEndViewingDate()).isNotNull();
        assertThat(any.getProperties().getAcquisition().getMission()).isNotNull();
    }
}
