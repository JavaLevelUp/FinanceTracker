terraform {
  source = "tfr://registry.terraform.io/terraform-aws-modules/s3-bucket/aws//?version=4.1.1"
}

include "root" {
  path = find_in_parent_folders()
}

inputs = {
  bucket = "finance-tracker-environment-variables"
}
