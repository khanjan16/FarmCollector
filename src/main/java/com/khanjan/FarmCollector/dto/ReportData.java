package com.khanjan.FarmCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportData {
    private String farmName;
    private String cropType;
    private double expectedProduct;
    private double actualProduct;
    private double variance;

    public ReportData(String farmName, String cropType, double expectedProduct, double actualProduct) {
        this.farmName = farmName;
        this.cropType = cropType;
        this.expectedProduct = expectedProduct;
        this.actualProduct = actualProduct;
        this.variance = actualProduct - expectedProduct;
    }
}
