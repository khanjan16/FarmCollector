package com.khanjan.FarmCollector;

import com.khanjan.FarmCollector.controller.FarmCollectorController;
import com.khanjan.FarmCollector.dto.ReportData;
import com.khanjan.FarmCollector.services.FarmDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for FarmCollectorController to verify API functionality.
 */
@WebMvcTest(FarmCollectorController.class)
public class FarmCollectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FarmDataService farmDataService;

    /**
     * Test to verify the functionality of the '/FarmCollection/planted' endpoint.
     * This test ensures that planted data is recorded successfully.
     *
     * @throws Exception if the request or validation fails.
     */
    @Test
    public void testRecordPlantedData() throws Exception {
        String requestBody = "{" +
                "\"farmName\":\"MyFarm\"," +
                "\"season\":\"Spring\"," +
                "\"cropType\":\"Corn\"," +
                "\"plantingArea\":100," +
                "\"expectedProduct\":500" +
                "}";

        mockMvc.perform(post("/FarmCollection/planted")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Planted data recorded successfully"));
    }

    /**
     * Test to verify the functionality of the '/FarmCollection/harvested' endpoint.
     * This test ensures that harvested data is recorded successfully.
     *
     * @throws Exception if the request or validation fails.
     */
    @Test
    public void testRecordHarvestedData() throws Exception {
        String requestBody = "{" +
                "\"farmName\":\"MyFarm\"," +
                "\"season\":\"Spring\"," +
                "\"cropType\":\"Corn\"," +
                "\"actualHarvestedProduct\":480" +
                "}";

        mockMvc.perform(post("/FarmCollection/harvested")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Harvested data recorded successfully"));
    }

    /**
     * Test to verify the functionality of the '/FarmCollection/reports' endpoint.
     * This test ensures that reports are generated correctly for a given season.
     *
     * @throws Exception if the request or validation fails.
     */
    @Test
    public void testGenerateReports() throws Exception {
        List<ReportData> mockReports = Arrays.asList(
                new ReportData("MyFarm", "Corn", 500, 480),
                new ReportData("MyFarm", "Potato", 300, 290)
        );

        Mockito.when(farmDataService.generateReportsBySeason("Spring")).thenReturn(mockReports);

        mockMvc.perform(get("/FarmCollection/reports").contentType(MediaType.APPLICATION_JSON)
                        .param("season", "Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].farmName").value("MyFarm"))
                .andExpect(jsonPath("$[0].cropType").value("Corn"))
                .andExpect(jsonPath("$[0].expectedProduct").value(500))
                .andExpect(jsonPath("$[0].actualProduct").value(480))
                .andExpect(jsonPath("$[1].cropType").value("Potato"));
    }
}