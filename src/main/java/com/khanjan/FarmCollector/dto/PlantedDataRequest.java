package com.khanjan.FarmCollector.dto;

import lombok.Data;

@Data
public class PlantedDataRequest {
    private String farmName;
    private String season;
    private String cropType;
    private double plantingArea;
    private double expectedProduct;
}
