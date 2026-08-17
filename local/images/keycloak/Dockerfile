### Build
FROM quay.io/keycloak/keycloak:26.7.1 AS builder

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_TRACING_ENABLED=true

ENV KC_TELEMETRY_METRICS_ENABLED=true
ENV KC_TELEMETRY_LOGS_ENABLED=true
ENV KC_FEATURES=opentelemetry-metrics:v1,opentelemetry-logs:v1

# Configure a database vendor
ENV KC_DB=postgres
RUN /opt/keycloak/bin/kc.sh build

### Runtime image
FROM quay.io/keycloak/keycloak:26.7.1
COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]