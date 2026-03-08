# Remote Command Execution Simulator

This project provides a simple backend service that allows submitting shell commands for execution and tracking their status.

---

## Architecture: Asynchronous job queue with a single worker thread

![Diagram](./diagram.drawio.svg)

The service manages command executions with the following statuses:

- `QUEUED`
- `IN_PROGRESS`
- `FINISHED`
- `FAILED`

---

## Running the Application

To execute the application locally, run the following command from the **root folder** of the project:

```bash
mvn spring-boot:run
```

Once the application is running, it will be available at:

```
http://localhost:8080
```

## Running the Application with Docker

To run the application using Docker, execute the following commands from the project root:

```bash
chmod +x ./run-docker.sh
./run-docker.sh
```

---

## API Usage

The API allows you to submit commands and check their execution status.

### 1. Submit a Command

Execute the following request to create a new execution:

```bash
curl -X POST http://localhost:8080/api/executions \
  -H "Content-Type: application/json" \
  -d '{"command":"sleep 20 && echo done","cpu":1}'; echo
```

The backend will respond with a JSON object similar to:

```json
{ "id": "XXX" }
```

The `id` will be used to query the execution status.

---

### 2. Check Execution Status

Use the returned `id` to check the status of the execution:

```bash
curl http://localhost:8080/api/executions/XXX; echo
```

Example response:

```json
{
  "id": "XXX",
  "command": "sleep 20 && echo done",
  "cpu": 1,
  "status": "STATUS",
  "output": "message",
  "error": "error-message"
}
```

This response contains all relevant information about the execution.

---

### 3. Example of a Failed Command

You can trigger a failed execution using the following command:

```bash
curl -X POST http://localhost:8080/api/executions \
  -H "Content-Type: application/json" \
  -d '{"command":"cat file-that-does-not-exist","cpu":1}'; echo
```

---

## How to Check Each Status

### QUEUED

1. Execute the command submission **two or three times**:

```bash
(1)
```

2. Then check the status:

```bash
(2)
```

If the system queue is busy, some executions will remain in `QUEUED`.

---

### IN_PROGRESS

1. Submit a command:

```bash
(1)
```

2. Immediately check its status:

```bash
(2)
```

Alternatively, increase the sleep time in the command:

```json
"sleep 60 && echo done"
```

Then check the `IN_PROGRESS` status while it is still running.

---

### FINISHED

1. Submit a command:

```bash
(1)
```

2. Wait for a few seconds.

3. Check the status:

```bash
(2)
```

The execution should appear as `FINISHED`.

---

### FAILED

1. Execute the failing command:

```bash
(3)
```

2. Then check the status:

```bash
(2)
```

The execution will appear as `FAILED`.

---

## Example Workflow

Typical usage flow:

1. Submit a command → receive an `id`
2. Use the `id` to check the execution status
3. Inspect the `status`, `output`, or `error` fields

---

## Status Summary

| Status | Description |
|------|------|
| `QUEUED` | The command is waiting to be executed |
| `IN_PROGRESS` | The command is currently running |
| `FINISHED` | The command finished successfully |
| `FAILED` | The command execution failed |

---