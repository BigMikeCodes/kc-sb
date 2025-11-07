package dev.michaelfarrant.kcsb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakConfiguration(
        String url,
        String realm,
        String clientId,
        String clientSecret
) {
}
