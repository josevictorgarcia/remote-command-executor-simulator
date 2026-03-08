package com.example.backend.dto;

import com.example.backend.model.ExecutionStatus;

public class ExecutionStatusResponse {
    private String id;
    private String command;
    private int cpu;
    private ExecutionStatus status;
    private String output;
    private String error;

    public ExecutionStatusResponse(String id, String command, int cpu, ExecutionStatus status, String output, String error) {
        this.id = id;
        this.command = command;
        this.cpu = cpu;
        this.status = status;
        this.output = output;
        this.error = error;
    }

    public String getId() { return id; }
    public String getCommand() { return command; }
    public int getCpu() { return cpu; }
    public ExecutionStatus getStatus() { return status; }
    public String getOutput() { return output; }
    public String getError() { return error; }
    public void setId(String id) { this.id = id; }
    public void setCommand(String command) { this.command = command; } 
    public void setCpu(int cpu) { this.cpu = cpu; }
    public void setStatus(ExecutionStatus status) { this.status = status; }
    public void setOutput(String output) { this.output = output; }
    public void setError(String error) { this.error = error; }
}
