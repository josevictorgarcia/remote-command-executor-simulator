package com.example.backend.service;

import com.example.backend.model.Execution;
import com.example.backend.storage.ExecutionRepository;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {

    private final ExecutionRepository repository;
    private final ExecutorManager executorManager;

    public ExecutionService(ExecutionRepository repository, ExecutorManager executorManager) {
        this.repository = repository;
        this.executorManager = executorManager;
    }

    public Execution createExecution(String command, int cpu) {
        Execution execution = new Execution(command, cpu);
        repository.save(execution);

        // Ahora enviamos la ejecución a la cola real
        executorManager.submitExecution(execution);

        return execution;
    }

    public Execution getExecution(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Execution not found: " + id));
    }
}
