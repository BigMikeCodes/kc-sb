# iac to configure a local keycloak server

terraform {
  required_providers {
    keycloak = {
      source  = "keycloak/keycloak"
      version = "5.9.0"
    }
  }
}

provider "keycloak" {
  url       = "http://keycloak:8080"
  client_id = "admin-cli"
  username  = "admin"
  password  = "admin"
}

resource "keycloak_realm" "kc_sb_realm" {
  realm = "kc-sb"
  display_name = "Keycloak Spring Boot demo"
  enabled = true

  # login
  registration_allowed = false
  registration_email_as_username = true
  login_with_email_allowed = true
  reset_password_allowed = true

  # tokens
  revoke_refresh_token = true

  # sso
  sso_session_idle_timeout = "24h"
  sso_session_max_lifespan = "168h"
}

data "keycloak_openid_client" "realm_management_client" {
    realm_id = keycloak_realm.kc_sb_realm.id
    client_id = "realm-management"
}

# General purpose public client that can be used to call the api
resource "keycloak_openid_client" "default_client" {
  realm_id = keycloak_realm.kc_sb_realm.id
  client_id = "default-client"
  name = "Default Client"
  access_type = "PUBLIC"
  valid_redirect_uris = [ "https://127.0.0.1:5173/oidc/login-callback" ]
  valid_post_logout_redirect_uris = [ "https://127.0.0.1:5173/oidc/login-callback" ]
  pkce_code_challenge_method = "S256"
  use_refresh_tokens = true

  # Flows
  standard_flow_enabled = true
  direct_access_grants_enabled = true
}

# Service account - used by the api
resource "keycloak_openid_client" "api_service_account_client" {
  realm_id = keycloak_realm.kc_sb_realm.id
  client_id = "api-service-account"
  name = "Api Service Account"
  access_type = "CONFIDENTIAL"

  # flows
  service_accounts_enabled = true
}

resource "keycloak_openid_client_service_account_role" "api_service_account_role" {
  for_each = toset(var.api_service_account_roles)

  realm_id = keycloak_realm.kc_sb_realm.id
  client_id = data.keycloak_openid_client.realm_management_client.id
  service_account_user_id = keycloak_openid_client.api_service_account_client.service_account_user_id
  role = each.value
}

resource "keycloak_role" "demo_application_role" {
  for_each = toset(var.roles)

  realm_id    = keycloak_realm.kc_sb_realm.id
  name        = each.value
  description = "Application role ${each.value}"
}

resource "keycloak_group" "admin_group" {
  realm_id = keycloak_realm.kc_sb_realm.id
  name = "admin"
}
