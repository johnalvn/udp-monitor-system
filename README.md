
# Sensor monitoring system

A simple monitoring system Using Kotlin and rabbitmq



## Features

- Uese Kotlin
- Use kotlin coroutines Flow for handling reactivity
- Uses rabbitmq for integration
- Uses snakeyaml for handling config properites.
- Uses strategy pattern for notification.
- Use docker and docker compose to easily run the project.
- Use gradle as build system.
- Uses Junit for testing.
- Uses regex to validate input message



## Run Locally

Clone the project

```bash
  docker-compose up
```

Open a terminal and run

```bash
  echo "sensor_id=t1; value=30" | nc -u -w1 127.0.0.1 3344
  echo "sensor_id=t1; value=30" | nc -u -w1 127.0.0.1 3344
  echo "sensor_id=t1; value=30" | nc -u -w1 127.0.0.1 3344
  echo "sensor_id=t1; value=30" | nc -u -w1 127.0.0.1 3344
  echo "sensor_id=t1; value=30" | nc -u -w1 127.0.0.1 3344
```
