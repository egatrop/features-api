package com.yivanou.featureservice.mapper;

import com.yivanou.featureservice.repository.entity.Feature;
import com.yivanou.featureservice.service.dto.FeatureDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeaturesMapper {

   public FeatureDto entity2Dto(Feature entity) {
      return FeatureDto.builder()
              .id(entity.getProperties().getId())
              .beginViewingDate(entity.getProperties().getAcquisition().getBeginViewingDate())
              .endViewingDate(entity.getProperties().getAcquisition().getEndViewingDate())
              .missionName(entity.getProperties().getAcquisition().getMission())
              .timestamp(entity.getProperties().getTimestamp())
              .build();
   }

   public List<FeatureDto> entities2Dtos(List<Feature> entities) {
      return entities.stream().map(this::entity2Dto).collect(Collectors.toList());
   }
}
