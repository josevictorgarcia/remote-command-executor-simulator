#!/bin/bash

# Build Docker image
docker build -t remote-executor .

# Run using Docker Compose
docker compose up --build
