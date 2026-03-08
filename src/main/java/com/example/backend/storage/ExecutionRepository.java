package com.example.backend.storage;

import com.example.backend.model.Execution;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ExecutionRepository {

    private final Map<String, Execution> storage = new ConcurrentHashMap<>();

    public Execution save(Execution execution) {
        storage.put(execution.getId(), execution);
        return execution;
    }

    public Optional<Execution> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }
}
