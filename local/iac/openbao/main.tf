terraform {
  required_providers {
    vault = {
      source = "hashicorp/vault"
      version = "5.11.0" 
    }
  }

  backend "local" {
    path = "/tofu/state/tofu.state"
  }
}

provider "vault" {
  address = "http://ob:8200"
  auth_login {
    path = "/auth/approle/login"
    parameters = {
      role_id   = "local-dev-tofu-role-id"
      secret_id = "local-dev-tofu-secret-id"
    }
  }
}

# add the kv secrets engine
resource "vault_mount" "app_kv2" {
  path = "app-secrets"
  type = "kv-v2"
  options = {
    version = "2"
    type    = "kv-v2"
  }
  description = "Application secrets"
}

resource "vault_mount" "pki" {
  path = "acme-pki"
  type = "pki"
  description = "acme pki engine, issues self signed certs for local dev"
}