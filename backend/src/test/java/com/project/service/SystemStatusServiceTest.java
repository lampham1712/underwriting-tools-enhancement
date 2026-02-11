package com.project.service;

import com.project.dto.StatusSummary;
import com.project.model.Status;
import com.project.model.SystemStatus;
import com.project.repository.SystemStatusLogRepository;
import com.project.repository.SystemStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemStatusServiceTest {

    @Mock
    private SystemStatusRepository statusRepository;
    @Mock
    private SystemStatusLogRepository logRepository;

    private SystemStatusService service;

    @BeforeEach
    void setUp() {
        // We pass an empty list of strategies for simple service tests
        service = new SystemStatusService(statusRepository, logRepository, Collections.emptyList());
    }

    @Test
    void testGetSummary_CalculatesCorrectly() {
        // Arrange
        // Using setters if Entity is standard Class, assuming @Data or setters exist
        SystemStatus s1 = new SystemStatus(); s1.setStatus(Status.ONLINE);
        SystemStatus s2 = new SystemStatus(); s2.setStatus(Status.ONLINE);
        SystemStatus s3 = new SystemStatus(); s3.setStatus(Status.OFFLINE);
        
        when(statusRepository.findAll()).thenReturn(Arrays.asList(s1, s2, s3));

        // Act
        StatusSummary summary = service.getSummary();

        // Assert
        assertEquals(3, summary.totalSystems());
        assertEquals(2, summary.onlineCount());
        assertEquals(1, summary.offlineCount());
    }
}
