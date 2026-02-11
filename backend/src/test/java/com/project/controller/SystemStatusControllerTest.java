package com.project.controller;

import com.project.dto.StatusSummary;
import com.project.service.SystemStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SystemStatusController.class)
class SystemStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemStatusService service;

    @Test
    void getSummary_ReturnsJson() throws Exception {
        // Arrange
        StatusSummary mockSummary = new StatusSummary(10, 8, 2, StatusSummary.GlobalStatus.PARTIAL);

        when(service.getSummary()).thenReturn(mockSummary);

        // Act & Assert
        mockMvc.perform(get("/api/system-status/summary")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSystems").value(10))
                .andExpect(jsonPath("$.onlineCount").value(8))
                .andExpect(jsonPath("$.globalStatus").value("PARTIAL"));
    }
}
