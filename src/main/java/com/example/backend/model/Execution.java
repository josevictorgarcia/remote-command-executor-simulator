package com.example.backend.model;

import java.time.Instant;
import java.util.UUID;

public class Execution {

    private String id;
    private String command;
    private int cpu;
    private ExecutionStatus status;
    private String output;
    private String error;
    private Instant createdAt;
    private Instant startedAt;
    private Instant finishedAt;

    public Execution(String command, int cpu) {
        this.id = UUID.randomUUID().toString();
        this.command = command;
        this.cpu = cpu;
        this.status = ExecutionStatus.QUEUED;
        this.createdAt = Instant.now();
    }

    public String getId() { return id; }
    public String getCommand() { return command; }
    public int getCpu() { return cpu; }
    public ExecutionStatus getStatus() { return status; }
    public String getOutput() { return output; }
    public String getError() { return error; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getFinishedAt() { return finishedAt; }
    public void setStatus(ExecutionStatus status) { this.status = status; }
    public void setOutput(String output) { this.output = output; }
    public void setError(String error) { this.error = error; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
    public void setFinishedAt(Instant finishedAt) { this.finishedAt = finishedAt;}
    public void setCommand(String command) { this.command = command; }
    public void setCpu(int cpu) { this.cpu = cpu; }
    public void setId(String id) { this.id = id; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
