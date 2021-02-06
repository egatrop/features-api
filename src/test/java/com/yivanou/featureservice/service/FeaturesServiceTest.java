package com.yivanou.featureservice.service;

import com.yivanou.featureservice.mapper.FeaturesMapper;
import com.yivanou.featureservice.repository.FeaturesDataSource;
import com.yivanou.featureservice.repository.entity.Feature;
import com.yivanou.featureservice.repository.entity.Properties;
import com.yivanou.featureservice.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FeaturesService.class, FeaturesMapper.class, FeaturesDataSource.class})
class FeaturesServiceTest {

    @MockBean
    private FeaturesMapper mapper;

    @MockBean
    private FeaturesDataSource dataSource;

    @InjectMocks
    @Autowired
    private FeaturesService service;

    @Test
    void givenExistingFeature_whenGetById_thenShouldCallDataSourceAndMapper() {
        final UUID id = UUID.randomUUID();
        final Feature feature = Feature.builder().build();
        when(dataSource.findById(id)).thenReturn(Optional.of(feature));
        when(mapper.entity2Dto(feature)).thenAnswer(RETURNS_MOCKS);

        service.getFeatureById(id);

        verify(dataSource).findById(id);
        verify(mapper).entity2Dto(any());
    }

    @Test
    void givenFeatureNotExists_whenGetById_thenThrowsException() {
        final UUID id = UUID.randomUUID();
        when(dataSource.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getFeatureById(id));

        verify(dataSource).findById(id);
        verify(mapper, never()).entity2Dto(any());
    }

    @Test
    void givenListOfFeature_whenGetAll_thenCallDataSourceAndMapper() {
        final Feature feature = Feature.builder().build();
        final List<Feature> featureList = Collections.singletonList(feature);
        when(dataSource.getAll()).thenReturn(featureList);
        when(mapper.entities2Dtos(featureList)).thenAnswer(RETURNS_MOCKS);

        service.getAll();

        verify(dataSource).getAll();
        verify(mapper).entities2Dtos(featureList);
    }

    @Test
    void givenFeatureWithQuicklook_whenGetImageById_thenReturnDecodedString() {
        final String originalString = UUID.randomUUID().toString();
        final String encodedString = Base64.getEncoder().encodeToString(originalString.getBytes());
        final Feature feature = Feature.builder()
                .properties(Properties.builder()
                        .quicklook(encodedString).build()).build();
        final UUID id = UUID.randomUUID();

        when(dataSource.findById(id)).thenReturn(Optional.of(feature));

        byte[] result = service.getImage(id);

        assertThat(new String(result)).isEqualTo(originalString);
    }

    @Test
    void givenFeatureWithoutQuicklook_whenGetImageById_thenThrowsException() {
        final Feature feature = Feature.builder()
                .properties(Properties.builder()
                        .quicklook(null).build()).build();
        final UUID id = UUID.randomUUID();

        when(dataSource.findById(id)).thenReturn(Optional.of(feature));

        assertThrows(NotFoundException.class, () -> service.getImage(id));
    }
}
