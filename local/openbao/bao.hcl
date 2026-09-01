ui = true
cluster_addr = "http://127.0.0.1:8201"

seal "static" {
  current_key_id = "local-dev-static"
  current_key    = "file:///bao/config/local-dev-unseal.key"
}

storage "raft" {
  path    = "/openbao/file"
  node_id = "dev_node"
}

listener "tcp" {
  address     = "0.0.0.0:8200"
  tls_disable = "true"
}

initialize "bootstrap" {
  
  #####
  # opentofu
  #####
  request "enable_approle" {
    operation = "update"
    path      = "sys/auth/approle"
    data = { type = "approle" }
  }

  request "create_policy" {
    operation = "update"
    path      = "sys/policies/acl/tofu-policy"
    data = {
      policy = <<-EOP
        path "*" {
          capabilities = ["create", "read", "update", "delete", "list", "sudo"]
        }
      EOP
    }
  }

  request "create_approle" {
    operation = "update"
    path      = "auth/approle/role/tofu-role"
    data = { token_policies = ["tofu-policy"] }
  }

  # Force a known Role ID
  request "set_role_id" {
    operation = "update"
    path      = "auth/approle/role/tofu-role/role-id"
    data = { role_id = "local-dev-tofu-role-id" }
  }

  # Force a known Secret ID
  request "set_secret_id" {
    operation = "update"
    path      = "auth/approle/role/tofu-role/custom-secret-id"
    data = { secret_id = "local-dev-tofu-secret-id" }
  }

  #####
  # Admin UI
  #####
  request "enable_userpass" {
    operation = "update"
    path      = "sys/auth/userpass"
    data      = { type = "userpass" }
  }

  # Create a root-level policy for UI browsing
  request "create_ui_admin_policy" {
    operation = "update"
    path      = "sys/policies/acl/ui-admin-policy"
    data = {
      policy = <<-EOP
        path "*" {
          capabilities = ["create", "read", "update", "delete", "list", "sudo"]
        }
      EOP
    }
  }

  # Create user 'admin' with password 'password123'
  request "create_ui_user" {
    operation = "update"
    path      = "auth/userpass/users/admin"
    data = { 
      password = "password123"
      token_policies = ["ui-admin-policy"] 
    }
  }

}
