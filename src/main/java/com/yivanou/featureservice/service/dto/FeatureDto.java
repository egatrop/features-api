package com.yivanou.featureservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDto {
    private UUID id;
    private Long timestamp;
    private Long beginViewingDate;
    private Long endViewingDate;
    private String missionName;
}
