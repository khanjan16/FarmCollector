package com.khanjan.FarmCollector.repository;

import com.khanjan.FarmCollector.entity.HarvestedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestedDataRepository extends JpaRepository<HarvestedData, Long> {
    List<HarvestedData> findBySeason(String season);
    List<HarvestedData> findByCropType(String cropType);
}
