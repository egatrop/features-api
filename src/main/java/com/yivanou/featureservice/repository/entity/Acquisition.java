package com.yivanou.featureservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Acquisition {
    private Long endViewingDate;
    private Long beginViewingDate;
    private String mission;
}
