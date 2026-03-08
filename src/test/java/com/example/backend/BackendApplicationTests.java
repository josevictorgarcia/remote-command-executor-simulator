package com.example.backend;

import com.example.backend.model.Execution;
import com.example.backend.model.ExecutionStatus;
import com.example.backend.service.ExecutionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private ExecutionService executionService;

    // Test 1: El contexto carga correctamente
    @Test
    void contextLoads() {
    }

    // Test 2: Test del servicio: creación de ejecución en estado QUEUED
    @Test
    void shouldCreateQueuedExecution() {
        Execution exec = executionService.createExecution("echo test", 1);

        assertNotNull(exec.getId());
        assertEquals(ExecutionStatus.QUEUED, exec.getStatus());
        assertEquals("echo test", exec.getCommand());
    }
}
