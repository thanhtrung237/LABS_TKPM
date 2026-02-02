# Fault Tolerance Demo với Resilience4J

Dự án này demo các pattern fault tolerance sử dụng Resilience4J trong Spring Boot với 2 service:
- **Service A** (port 8080): Service chính gọi đến Service B
- **Service B** (port 8081): Service external được simulate với random failures

## Các Pattern được implement

### 1. Circuit Breaker
- Ngăn chặn cascade failures
- Tự động chuyển đổi giữa CLOSED/OPEN/HALF_OPEN states
- Failure rate threshold: 50%
- Wait duration: 5 seconds

### 2. Retry
- Tự động retry khi gặp lỗi
- Max attempts: 3
- Exponential backoff: 1s, 2s, 4s

### 3. Rate Limiter
- Giới hạn số request per second
- Limit: 10 requests/second
- Timeout: 3 seconds

### 4. Bulkhead
- Isolation resource pools
- Max concurrent calls: 5
- Max wait duration: 2 seconds

### 5. Time Limiter
- Timeout protection
- Timeout duration: 3 seconds

## Cách chạy

### 1. Start Service A (port 8080)
```bash
./gradlew bootRun
```

### 2. Service B được simulate trong cùng application
Service B controller sẽ có random failures để test fault tolerance patterns.

## API Endpoints

### Service A APIs
- `GET /api/service-a/users` - Lấy danh sách users
- `GET /api/service-a/users/{id}` - Lấy user theo ID
- `POST /api/service-a/users` - Tạo user mới
- `GET /api/service-a/process/{userId}` - Process user data
- `GET /api/service-a/health` - Health check

### Service B APIs (Simulated)
- `GET /api/users` - Mock external service
- `GET /api/users/{id}` - Mock get user by ID
- `POST /api/users` - Mock create user
- `GET /api/users/health` - Health check
- `GET /api/users/stats` - Request statistics

### Monitoring APIs
- `GET /api/fault-tolerance/status` - Fault tolerance metrics
- `GET /api/fault-tolerance/reset` - Reset circuit breakers
- `GET /api/fault-tolerance/info` - System info

### Actuator Endpoints
- `GET /actuator/health` - Application health
- `GET /actuator/circuitbreakers` - Circuit breaker metrics
- `GET /actuator/ratelimiters` - Rate limiter metrics
- `GET /actuator/bulkheads` - Bulkhead metrics
- `GET /actuator/retries` - Retry metrics

## Test Scenarios

### 1. Test Circuit Breaker
```bash
# Gọi nhiều lần để trigger failures
for i in {1..20}; do curl http://localhost:8080/api/service-a/users; echo; done

# Check circuit breaker status
curl http://localhost:8080/api/fault-tolerance/status
```

### 2. Test Rate Limiter
```bash
# Gọi nhanh liên tiếp để hit rate limit
for i in {1..15}; do curl http://localhost:8080/api/service-a/users/1 & done
```

### 3. Test Retry
```bash
# Monitor logs để thấy retry attempts
curl http://localhost:8080/api/service-a/users/1
```

### 4. Test Bulkhead
```bash
# Gọi concurrent requests
for i in {1..10}; do curl http://localhost:8080/api/service-a/process/1 & done
```

### 5. Create User Test
```bash
curl -X POST http://localhost:8080/api/service-a/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com"}'
```

## Configuration

Tất cả configuration trong `application.yml`:
- Circuit breaker settings
- Retry policies  
- Rate limiter configs
- Bulkhead settings
- Time limiter configs
- Actuator endpoints

## Logs

Application sẽ log chi tiết:
- Request/response flows
- Fault tolerance pattern activations
- Fallback method executions
- Circuit breaker state transitions
- Retry attempts

## Fallback Strategies

Khi Service B fail, system sẽ:
1. Return cached/default data
2. Log fallback reason
3. Continue serving requests
4. Maintain system stability

## Monitoring

- Real-time metrics qua Actuator endpoints
- Custom fault tolerance status endpoint
- Detailed logging for debugging
- Circuit breaker state monitoring