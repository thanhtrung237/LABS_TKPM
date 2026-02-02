@echo off
echo ========================================
echo Fault Tolerance Demo Test Script
echo ========================================
echo.

echo 1. Testing Service Health...
curl -s http://localhost:8080/api/service-a/health
echo.
echo.

echo 2. Getting System Info...
curl -s http://localhost:8080/api/fault-tolerance/info
echo.
echo.

echo 3. Testing Normal User Retrieval...
curl -s http://localhost:8080/api/service-a/users/1
echo.
echo.

echo 4. Testing Circuit Breaker (multiple calls)...
for /L %%i in (1,1,10) do (
    echo Call %%i:
    curl -s http://localhost:8080/api/service-a/users
    echo.
    timeout /t 1 /nobreak >nul
)
echo.

echo 5. Checking Fault Tolerance Status...
curl -s http://localhost:8080/api/fault-tolerance/status
echo.
echo.

echo 6. Testing Rate Limiter (rapid calls)...
echo Making rapid calls to trigger rate limiter...
for /L %%i in (1,1,15) do (
    start /B curl -s http://localhost:8080/api/service-a/users/1
)
timeout /t 3 /nobreak >nul
echo.

echo 7. Creating New User...
curl -s -X POST http://localhost:8080/api/service-a/users -H "Content-Type: application/json" -d "{\"name\":\"Test User\",\"email\":\"test@example.com\"}"
echo.
echo.

echo 8. Processing User Data...
curl -s http://localhost:8080/api/service-a/process/1
echo.
echo.

echo 9. Final Status Check...
curl -s http://localhost:8080/api/fault-tolerance/status
echo.
echo.

echo ========================================
echo Test completed! Check the logs for detailed fault tolerance behavior.
echo ========================================
pause