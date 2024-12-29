package com.khanjan.FarmCollector.dto;

import lombok.Data;

@Data
public class HarvestedDataRequest {
    private String farmName;
    private String season;
    private String cropType;
    private double actualHarvestedProduct;
}
