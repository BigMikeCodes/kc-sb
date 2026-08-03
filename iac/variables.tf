variable "roles" {
  description = "List of all assignable roles within job plan"
  type = list(string)
  default = [
    "admin-user", # class on "admin" hence -user on the end
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