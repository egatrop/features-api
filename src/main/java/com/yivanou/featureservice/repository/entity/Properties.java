package com.yivanou.featureservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Properties {
    private UUID id;
    private Long timestamp;
    private String quicklook;
    private Acquisition acquisition;
}
