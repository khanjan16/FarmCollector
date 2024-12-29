package com.khanjan.FarmCollector.controller;


import com.khanjan.FarmCollector.dto.HarvestedDataRequest;
import com.khanjan.FarmCollector.dto.PlantedDataRequest;
import com.khanjan.FarmCollector.dto.ReportData;
import com.khanjan.FarmCollector.services.FarmDataService;
import io.micrometer.common.util.StringUtils;
import org.apache.catalina.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/FarmCollection")
public class FarmCollectorController {
    @Autowired
    private FarmDataService farmDataService;

    /**
     * API to record planted data for the farm.
     * This endpoint receives a request body containing planted data and records it.
     *
     * @param request The request body containing the planted data.
     * @return A response containing a message indicating the success of the operation.
     */
    @PostMapping(value = "/planted",consumes={"application/json"})
    public ResponseEntity<Map<String, Object>> recordPlantedData(@RequestBody PlantedDataRequest request) {
        farmDataService.savePlantedData(request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Planted data recorded successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * API to record harvested data for the farm.
     * This endpoint receives a request body containing harvested data and records it.
     *
     * @param request The request body containing the harvested data.
     * @return A response containing a message indicating the success of the operation.
     */
    @PostMapping(value="/harvested", consumes={"application/json"})
    public ResponseEntity<Map<String, Object>> recordHarvestedData(@RequestBody HarvestedDataRequest request) {
        farmDataService.saveHarvestedData(request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Harvested data recorded successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * API to generate reports for a specific season.
     * This endpoint returns a list of reports based on the given season parameter.
     *
     * @param season The season for which the reports are to be generated.
     * @return A response containing a list of report data for the given season.
     */
    @GetMapping(value = "/reports", consumes = {"application/json"})
    public ResponseEntity<List<ReportData>> generateReports(@RequestParam (defaultValue = "") String season, @RequestParam( defaultValue = "") String cropType) {
        if(StringUtils.isNotEmpty(season) && StringUtils.isNotEmpty(cropType)) {
            return (ResponseEntity<List<ReportData>>) ResponseEntity.badRequest();
        } else if (StringUtils.isNotEmpty(season)) {
            List<ReportData> reports = farmDataService.generateReportsBySeason(season);
            return ResponseEntity.ok(reports);
        } else if (StringUtils.isNotEmpty(cropType)) {
            List<ReportData> reports = farmDataService.generateReportsByCrops(season);
            return ResponseEntity.ok(reports);
        } else {
            return (ResponseEntity<List<ReportData>>) ResponseEntity.badRequest();
        }

    }
}
