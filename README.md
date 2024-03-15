# FinanceTracker

## Running the CLI - On a linux system
1. Download the BEANCLI.deb file that is available in the release.
2. Open the folder which contains your BEANCLI.deb file in your terminal
3. Run the following command in your terminal
    ```sh
    export PATH=$PATH:/opt/beancli/bin
    ```
4. To run the CLI, run the following command in your terminal
    ```sh
    BEANCLI
    ```
## Infrastructure set up
Prerequisites:
* Terraform v1.5.7
* Terragrunt v0.53.2
1. Navigate to the ./infrastructure/directory
2. Set these environment variables on your system for use when creating the DB:
    * `TF_VAR_password`
    * `TF_VAR_username`
4. Run this command to build the infrastructure:
   ``` sh
   terragrunt run-all apply
   ```

Further Documentation:
https://grad-projects.atlassian.net/wiki/spaces/B/pages/2490379/Finance+Tracker+for+Beans
