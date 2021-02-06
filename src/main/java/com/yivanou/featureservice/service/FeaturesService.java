package com.yivanou.featureservice.service;

import com.yivanou.featureservice.mapper.FeaturesMapper;
import com.yivanou.featureservice.repository.FeaturesDataSource;
import com.yivanou.featureservice.service.dto.FeatureDto;
import com.yivanou.featureservice.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeaturesService {

    @Autowired
    private FeaturesDataSource dataSource;

    @Autowired
    private FeaturesMapper mapper;

    public FeatureDto getFeatureById(UUID id) {
        return dataSource.findById(id)
                .map(mapper::entity2Dto)
                .orElseThrow(() -> new NotFoundException(String.format("Feature with id=%s is not found", id.toString())));
    }

    public List<FeatureDto> getAll() {
        return mapper.entities2Dtos(dataSource.getAll());
    }

    public byte[] getImageAsBase64(UUID id) {
        return dataSource.findById(id)
                .filter(f -> f.getProperties().getQuicklook() != null)
                .map(f -> Base64.getDecoder().decode(f.getProperties().getQuicklook()))
                .orElseThrow(() -> new NotFoundException(String.format("Quick look for feature with id=%s is not found", id.toString())));
    }
}
