package com.example.backend.controller;

import com.example.backend.dto.ExecuteRequest;
import com.example.backend.dto.ExecuteResponse;
import com.example.backend.dto.ExecutionStatusResponse;
import com.example.backend.model.Execution;
import com.example.backend.service.ExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/executions")
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping
    public ResponseEntity<ExecuteResponse> execute(@RequestBody ExecuteRequest request) {
        Execution execution = executionService.createExecution(
                request.getCommand(),
                request.getCpu()
        );
        return ResponseEntity.ok(new ExecuteResponse(execution.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExecutionStatusResponse> getStatus(@PathVariable String id) {
        Execution execution = executionService.getExecution(id);

        return ResponseEntity.ok(
                new ExecutionStatusResponse(
                        execution.getId(),
                        execution.getCommand(),
                        execution.getCpu(),
                        execution.getStatus(),
                        execution.getOutput(),
                        execution.getError()
                )
        );
    }
}
