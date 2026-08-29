ui = true

seal "static" {
  current_key_id = "local-dev-static"
  current_key    = "file:///bao/config/local-dev-unseal.key"
}

storage "file" {
  path = "/openbao/file"
}

listener "tcp" {
  address     = "0.0.0.0:8200"
  tls_disable = "true"
}