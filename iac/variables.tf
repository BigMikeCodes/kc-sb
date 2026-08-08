variable "roles" {
  description = "List of all assignable roles within job plan"
  type = list(string)
  default = [
    "admin-user", # clash on "admin" hence -user on the end
    "member" # "standard" user
  ]
}

variable "api_service_account_roles" {
  description = "Permissions for the service account"
  type = list(string)
  default = [ 
    "manage-users",
    "manage-realm"
  ]
}

variable "login_redirect_uris" {
  description = "List of valid redirect uris for the default client"
  type = list(string)
  default = [
    "http://api.localhost/oidc/login-callback",
    "http://api.127.0.0.1/oidc/login-callback"
  ]
}