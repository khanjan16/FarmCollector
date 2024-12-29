package com.khanjan.FarmCollector.repository;

import com.khanjan.FarmCollector.entity.PlantedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantedDataRepository extends JpaRepository<PlantedData, Long> {
    List<PlantedData> findBySeason(String season);
    List<PlantedData> findByCropType(String cropType);
}
