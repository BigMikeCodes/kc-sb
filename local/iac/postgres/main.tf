terraform {
  required_providers {
    postgresql = {
      source  = "cyrilgdn/postgresql"
      version = "1.27.0"
    }
  }
  backend "local" {
    path = "/tofu/state/tofu.state"
  }
}

provider "postgresql" {
  host     = "pg"
  port     = 5432
  database = "postgres"
  username = "postgres"
  password = "postgrespassword"
  sslmode  = "disable"
}


### Database Owner
resource "postgresql_role" "kc_owner" {
  name  = "kc_owner"
  login = false
}

resource "postgresql_database" "kc" {
  name              = "kc"
  owner             = postgresql_role.kc_owner.name
  lc_collate        = "en_US.UTF-8"
  lc_ctype          = "en_US.UTF-8"
  connection_limit  = -1
  allow_connections = true
}

## Role for the keycloak (not login)
resource "postgresql_role" "keycloak_application" {
  name  = "keycloak_application"
  login = false
}

resource "postgresql_grant" "database" {
  database    = postgresql_database.kc.name
  role        = postgresql_role.keycloak_application.name
  schema      = "public"
  object_type = "database"
  privileges  = ["CONNECT", "TEMPORARY"]
}

resource "postgresql_grant" "schema" {
  database    = postgresql_database.kc.name
  role        = postgresql_role.keycloak_application.name
  schema      = "public"
  object_type = "schema"
  privileges  = ["CREATE", "USAGE"]
}

resource "postgresql_grant" "table" {
  database    = postgresql_database.kc.name
  role        = postgresql_role.keycloak_application.name
  schema      = "public"
  object_type = "table"
  privileges  = ["SELECT", "INSERT", "UPDATE", "DELETE", "TRUNCATE", "REFERENCES", "TRIGGER"]
}

resource "postgresql_grant" "sequence_grant" {
  database    = postgresql_database.kc.name
  role        = postgresql_role.keycloak_application.name
  schema      = "public"
  object_type = "sequence"
  privileges  = ["SELECT", "UPDATE", "USAGE"]
}

# The actual login
resource "postgresql_role" "keycloak" {
  name                = "keycloak"
  login               = true
  roles               = [postgresql_role.keycloak_application.name]
  password_wo         = "password123"
  password_wo_version = 1
}
