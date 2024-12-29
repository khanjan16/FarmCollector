package com.khanjan.FarmCollector.services;

import com.khanjan.FarmCollector.dto.HarvestedDataRequest;
import com.khanjan.FarmCollector.dto.PlantedDataRequest;
import com.khanjan.FarmCollector.dto.ReportData;
import com.khanjan.FarmCollector.entity.HarvestedData;
import com.khanjan.FarmCollector.entity.PlantedData;
import com.khanjan.FarmCollector.repository.HarvestedDataRepository;
import com.khanjan.FarmCollector.repository.PlantedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FarmDataService {

    @Autowired
    private PlantedDataRepository plantedDataRepository;

    @Autowired
    private HarvestedDataRepository harvestedDataRepository;

    /**
     * Saves the planted data into the database or persistent storage.
     *
     * @param request The request object containing details about the planted data,
     *                such as crop type, area, planting date, and quantity.
     * @return A boolean indicating whether the planted data was saved successfully.
     */
    public void savePlantedData(PlantedDataRequest request) {
        PlantedData plantedData = new PlantedData();
        plantedData.setFarmName(request.getFarmName());
        plantedData.setSeason(request.getSeason());
        plantedData.setCropType(request.getCropType());
        plantedData.setPlantingArea(request.getPlantingArea());
        plantedData.setExpectedProduct(request.getExpectedProduct());
        plantedDataRepository.save(plantedData);
    }

    /**
     * Saves the harvested data into the database or persistent storage.
     *
     * @param request The request object containing details about the harvested data,
     *                such as crop type, area, harvest date, and quantity.
     * @return A boolean indicating whether the harvested data was saved successfully.
     */
    public void saveHarvestedData(HarvestedDataRequest request) {
        HarvestedData harvestedData = new HarvestedData();
        harvestedData.setFarmName(request.getFarmName());
        harvestedData.setSeason(request.getSeason());
        harvestedData.setCropType(request.getCropType());
        harvestedData.setActualHarvestedProduct(request.getActualHarvestedProduct());
        harvestedDataRepository.save(harvestedData);
    }

    /**
     * Generates reports based on the provided season.
     *
     * @param season The season for which the reports are to be generated.
     *               For example, "Summer2024" or "Winter2024".
     * @return A list of {@link ReportData} objects representing the reports
     *         for the given season.
     */
    public List<ReportData> generateReportsBySeason(String season) {
        List<PlantedData> plantedDataList = plantedDataRepository.findBySeason(season);
        List<HarvestedData> harvestedDataList = harvestedDataRepository.findBySeason(season);

        Map<String, Double> actualProductMap = harvestedDataList.stream()
                .collect(Collectors.toMap(
                        data -> data.getFarmName() + "-" + data.getCropType(),
                        HarvestedData::getActualHarvestedProduct
                ));

        return plantedDataList.stream()
                .map(plantedData -> {
                    String key = plantedData.getFarmName() + "-" + plantedData.getCropType();
                    double actualProduct = actualProductMap.getOrDefault(key, 0.0);
                    return new ReportData(
                            plantedData.getFarmName(),
                            plantedData.getCropType(),
                            plantedData.getExpectedProduct(),
                            actualProduct
                    );
                })
                .collect(Collectors.toList());
    }

    public List<ReportData> generateReportsByCrops(String cropType) {
        List<PlantedData> plantedDataList = plantedDataRepository.findByCropType(cropType);
        List<HarvestedData> harvestedDataList = harvestedDataRepository.findByCropType(cropType);

        Map<String, Double> actualProductMap = harvestedDataList.stream()
                .collect(Collectors.toMap(
                        data -> data.getFarmName() + "-" + data.getCropType(),
                        HarvestedData::getActualHarvestedProduct
                ));

        return plantedDataList.stream()
                .map(plantedData -> {
                    String key = plantedData.getFarmName() + "-" + plantedData.getCropType();
                    double actualProduct = actualProductMap.getOrDefault(key, 0.0);
                    return new ReportData(
                            plantedData.getFarmName(),
                            plantedData.getCropType(),
                            plantedData.getExpectedProduct(),
                            actualProduct
                    );
                })
                .collect(Collectors.toList());
    }
}
