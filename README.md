
## Run Locally

Clone the project

```bash
  docker-compose up
```

Open a terminal and run

```bash
  echo "sensor_id=t1; value=30" | nc -u -w1 127.0.0.1 3344
  echo "sensor_id=t1; value=50" | nc -u -w1 127.0.0.1 3344
  echo "sensor_id=h1; value=30" | nc -u -w1 127.0.0.1 3344
  echo "sensor_id=h1; value=90" | nc -u -w1 127.0.0.1 3344
  echo "sensor_id=r1; value=30" | nc -u -w1 127.0.0.1 3344
```

You can change the thresholds in the config.yaml file:


```yaml
# monitoring-service/src/main/resources/config.yaml

sensor:
  temperature:
    threshold: 35
  humidity:
    threshold: 50
  msgTemplate:

```

then run the service again

Please following console log messages on both services

Monitoring service ALERT

```bash
monitoring-service-1  | [ALERT] [CRITICAL] Temperature of sensor t1 has crossed the threshold 35 , current value = 50 (2025-03-17T05:39:23.475Z)

``` 

Warehouse service Errors in messages format or port mismatching

```bash
warehouse-service-1  | Errors: Invalid sensor data format
```