package com.example.reward.controller;
import com.example.reward.controller.RewardController;
import com.example.reward.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("unused")
class RewardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();
    }

    @Test
    void testGetCustomerRewards_ValidRequest() throws Exception {
        // Mock input values
        Long customerId = 123L;
        String start = "2023-01-01T00:00:00";
        String end = "2023-12-31T23:59:59";
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);

        // Mock service behavior
        Map<Month, Integer> rewardsMap = new HashMap<>();
        rewardsMap.put(Month.JANUARY, 100);
        rewardsMap.put(Month.FEBRUARY, 200);

        when(rewardService.calculateMonthlyRewards(customerId, startDate, endDate))
                .thenReturn(rewardsMap);

        // Perform GET request and verify response
        mockMvc.perform(get("/api/rewards/{customerId}", customerId)
                .param("start", start)
                .param("end", end)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.JANUARY").value(100))
                .andExpect(jsonPath("$.FEBRUARY").value(200));
    }

    @Test
    void testGetCustomerRewards_InvalidDateFormat() throws Exception {
        // Mock invalid date format
        Long customerId = 123L;
        String invalidStart = "2023-01-01";
        String invalidEnd = "2023-12-31";

        // Perform GET request and expect Bad Request status
        mockMvc.perform(get("/api/rewards/{customerId}", customerId)
                .param("start", invalidStart)
                .param("end", invalidEnd)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCustomerRewards_MissingDateParams() throws Exception {
        // Mock missing request parameters
        Long customerId = 123L;

        // Perform GET request and expect Bad Request status due to missing date parameters
        mockMvc.perform(get("/api/rewards/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCustomerRewards_EmptyRewards() throws Exception {
        // Mock input values
        Long customerId = 123L;
        String start = "2023-01-01T00:00:00";
        String end = "2023-12-31T23:59:59";
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);

        // Mock service behavior for no rewards
        Map<Month, Integer> rewardsMap = new HashMap<>();

        when(rewardService.calculateMonthlyRewards(customerId, startDate, endDate))
                .thenReturn(rewardsMap);

        // Perform GET request and verify response (empty JSON object)
        mockMvc.perform(get("/api/rewards/{customerId}", customerId)
                .param("start", start)
                .param("end", end)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetCustomerRewards_InternalServerError() throws Exception {
        // Mock input values
        Long customerId = 123L;
        String start = "2023-01-01T00:00:00";
        String end = "2023-12-31T23:59:59";
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);

        // Mock service behavior to throw an exception
        when(rewardService.calculateMonthlyRewards(customerId, startDate, endDate))
                .thenThrow(new RuntimeException("Internal Server Error"));

        // Perform GET request and expect Internal Server Error status
        mockMvc.perform(get("/api/rewards/{customerId}", customerId)
                .param("start", start)
                .param("end", end)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
