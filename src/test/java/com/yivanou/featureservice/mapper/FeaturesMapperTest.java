package com.yivanou.featureservice.mapper;

import com.yivanou.featureservice.repository.entity.Acquisition;
import com.yivanou.featureservice.repository.entity.Feature;
import com.yivanou.featureservice.repository.entity.Properties;
import com.yivanou.featureservice.service.dto.FeatureDto;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FeaturesMapperTest {

    private FeaturesMapper mapper = new FeaturesMapper();

    @Test
    public void givenEntity_whenMapToDto_thenReturnDto() {
        final Feature entity = createRandomEntity();

        final FeatureDto dto = mapper.entity2Dto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getProperties().getId());
        assertThat(dto.getTimestamp()).isEqualTo(entity.getProperties().getTimestamp());
        assertThat(dto.getBeginViewingDate()).isEqualTo(entity.getProperties().getAcquisition().getBeginViewingDate());
        assertThat(dto.getEndViewingDate()).isEqualTo(entity.getProperties().getAcquisition().getEndViewingDate());
        assertThat(dto.getMissionName()).isEqualTo(entity.getProperties().getAcquisition().getMission());
    }

    private Feature createRandomEntity() {
        return Feature.builder()
                .properties(Properties.builder()
                        .id(UUID.randomUUID())
                        .timestamp(new Random().nextLong())
                        .acquisition(Acquisition.builder()
                                .beginViewingDate(new Random().nextLong())
                                .endViewingDate(new Random().nextLong())
                                .mission(UUID.randomUUID().toString())
                                .build())
                        .build()
                ).build();
    }

}
