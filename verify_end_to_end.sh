#!/bin/bash
set -e

echo "=========================================="
echo "Credit Application Service E2E Verification"
echo "=========================================="
echo ""

# Colors for output
GREEN='\033[0.32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Start services
echo -e "${YELLOW}Step 1: Starting Docker services...${NC}"
docker-compose up -d

# Wait for services to be healthy
echo -e "${YELLOW}Step 2: Waiting for services to be ready (60s)...${NC}"
sleep 60

# Check health
echo -e "${YELLOW}Step 3: Checking service health...${NC}"
HEALTH=$(curl -s http://localhost:8080/actuator/health | jq -r '.status')
if [ "$HEALTH" != "UP" ]; then
    echo "❌ Service is not healthy. Status: $HEALTH"
    docker-compose logs credit-application-service
    exit 1
fi
echo -e "${GREEN}✓ Service is healthy${NC}"

# Register affiliate
echo -e "${YELLOW}Step 4: Registering affiliate...${NC}"
REGISTER_RESPONSE=$(curl -s -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "password": "SecurePass123",
    "firstName": "John",
    "lastname": "Doe",
    "document": "123456789",
    "email": "john@example.com",
    "salary": 5000.00
  }')

TOKEN=$(echo $REGISTER_RESPONSE | jq -r '.token')
if [ "$TOKEN" == "null" ] || [ -z "$TOKEN" ]; then
    echo "❌ Failed to register affiliate"
    echo "Response: $REGISTER_RESPONSE"
    exit 1
fi
echo -e "${GREEN}✓ Affiliate registered. Token: ${TOKEN:0:30}...${NC}"

# Create credit application
echo -e "${YELLOW}Step 5: Creating credit application...${NC}"
APP_RESPONSE=$(curl -s -X POST http://localhost:8080/credit-applications \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 10000.00,
    "term": 12,
    "afilliateId": 1
  }')

APP_ID=$(echo $APP_RESPONSE | jq -r '.id')
if [ "$APP_ID" == "null" ] || [ -z "$APP_ID" ]; then
    echo "❌ Failed to create credit application"
    echo "Response: $APP_RESPONSE"
    exit 1
fi
echo -e "${GREEN}✓ Credit application created. ID: $APP_ID${NC}"

# Evaluate application
echo -e "${YELLOW}Step 6: Evaluating credit application...${NC}"
EVAL_RESPONSE=$(curl -s -X POST http://localhost:8080/credit-applications/$APP_ID/evaluate \
  -H "Authorization: Bearer $TOKEN")

STATUS=$(echo $EVAL_RESPONSE | jq -r '.status')
if [ "$STATUS" == "null" ] || [ -z "$STATUS" ]; then
    echo "❌ Failed to evaluate application"
    echo "Response: $EVAL_RESPONSE"
    exit 1
fi
echo -e "${GREEN}✓ Application evaluated. Status: $STATUS${NC}"

# Fetch Prometheus metrics
echo -e "${YELLOW}Step 7: Fetching Prometheus metrics...${NC}"
METRICS=$(curl -s http://localhost:8080/actuator/prometheus)
if echo "$METRICS" | grep -q "http_server_requests"; then
    echo -e "${GREEN}✓ Metrics are available${NC}"
    echo "Sample metrics:"
    echo "$METRICS" | grep -E "(http_server_requests_seconds_count|jvm_memory_used_bytes)" | head -5
else
    echo "❌ Metrics not found"
    exit 1
fi

# Check Prometheus
echo -e "${YELLOW}Step 8: Checking Prometheus...${NC}"
PROM_HEALTH=$(curl -s http://localhost:9090/-/healthy)
if [ "$PROM_HEALTH" == "Prometheus is Healthy." ]; then
    echo -e "${GREEN}✓ Prometheus is healthy${NC}"
else
    echo "⚠️  Prometheus may not be ready"
fi

# Check Grafana
echo -e "${YELLOW}Step 9: Checking Grafana...${NC}"
GRAFANA_HEALTH=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:3000/api/health)
if [ "$GRAFANA_HEALTH" == "200" ]; then
    echo -e "${GREEN}✓ Grafana is accessible${NC}"
else
    echo "⚠️  Grafana may not be ready (HTTP $GRAFANA_HEALTH)"
fi

echo ""
echo "=========================================="
echo -e "${GREEN}✓ E2E Verification Complete!${NC}"
echo "=========================================="
echo ""
echo "Access Points:"
echo "  • Application:        http://localhost:8080"
echo "  • Swagger UI:         http://localhost:8080/swagger-ui.html"
echo "  • Health:             http://localhost:8080/actuator/health"
echo "  • Metrics:            http://localhost:8080/actuator/prometheus"
echo "  • Prometheus:         http://localhost:9090"
echo "  • Grafana:            http://localhost:3000 (admin/admin)"
echo ""
echo "To view logs:          docker-compose logs -f credit-application-service"
echo "To stop services:      docker-compose down"
echo "To stop and clean:     docker-compose down -v"
echo ""
