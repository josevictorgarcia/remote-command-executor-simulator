package com.example.backend.dto;

public class ExecuteRequest {
    private String command;
    private int cpu;

    public String getCommand() { return command; }
    public int getCpu() { return cpu; }
}