package com.example.backend.service;

import com.example.backend.model.Execution;
import com.example.backend.model.ExecutionStatus;
import com.example.backend.storage.ExecutionRepository;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ExecutorManager {

    private final ExecutionRepository repository;

    // Cola real: un solo hilo ejecutando tareas en orden FIFO
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ExecutorManager(ExecutionRepository repository) {
        this.repository = repository;
    }

    // Enviar ejecución a la cola
    public void submitExecution(Execution execution) {
        executor.submit(() -> runExecution(execution));
    }

    private void runExecution(Execution execution) {
        try {
            // Esto permite ver el estado QUEUED durante un momento
            Thread.sleep(500);

            execution.setStatus(ExecutionStatus.IN_PROGRESS);
            execution.setStartedAt(Instant.now());
            repository.save(execution);

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", execution.getCommand());
            Process process = pb.start();

            BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder output = new StringBuilder();
            StringBuilder error = new StringBuilder();

            String line;
            while ((line = outputReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            while ((line = errorReader.readLine()) != null) {
                error.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            execution.setFinishedAt(Instant.now());

            if (exitCode == 0) {
                execution.setStatus(ExecutionStatus.FINISHED);
                execution.setOutput(output.toString());
            } else {
                execution.setStatus(ExecutionStatus.FAILED);
                execution.setError("Exit code: " + exitCode + "\nOutput:\n" + error);
            }

            repository.save(execution);

        } catch (Exception e) {
            execution.setStatus(ExecutionStatus.FAILED);
            execution.setError("Exception: " + e.getMessage());
            repository.save(execution);
        }
    }
}
