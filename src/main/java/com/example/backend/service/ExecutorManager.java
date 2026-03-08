package com.example.backend.service;

import com.example.backend.model.Execution;
import com.example.backend.model.ExecutionStatus;
import com.example.backend.storage.ExecutionRepository;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;

@Component
public class ExecutorManager {

    private final ExecutionRepository repository;

    public ExecutorManager(ExecutionRepository repository) {
        this.repository = repository;
    }

    public void startExecutionAsync(Execution execution) {
        Thread thread = new Thread(() -> runExecution(execution));
        thread.start();
    }

    private void runExecution(Execution execution) {
        try {
            Thread.sleep(1000);

            execution.setStatus(ExecutionStatus.IN_PROGRESS);
            execution.setStartedAt(Instant.now());
            repository.save(execution);

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", execution.getCommand());
            pb.redirectErrorStream(true);
            Process process = pb.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();

            execution.setFinishedAt(Instant.now());
            if (exitCode == 0) {
                execution.setStatus(ExecutionStatus.FINISHED);
                execution.setOutput(output.toString());
            } else {
                execution.setStatus(ExecutionStatus.FAILED);
                execution.setError("Exit code: " + exitCode + "\nOutput:\n" + output);
            }

            repository.save(execution);

        } catch (Exception e) {
            execution.setStatus(ExecutionStatus.FAILED);
            execution.setError("Exception: " + e.getMessage());
            repository.save(execution);
        }
    }
}
