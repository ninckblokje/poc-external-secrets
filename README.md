#  poc-external-secrets

This PoC shows the usage of the [external-secrets](https://external-secrets.io/) operator to retrieve secrets and certificates from Azure Key Vault using a workload managed identity.

Combined with [reloader](https://github.com/stakater/Reloader) pods can get automatically restarted once a secret changes.

## Setup

### K8S

1. Create namespace `poc-external-secrets`: `kubectl create ns poc-external-secrets`

### external-secrets

1. Create the following using the repo [my-azure-env](http://github.com/ninckblokje/my-azure-env)
   - VNet
   - Key vault
   - AKS
1. Install using the helm chart: `helm install external-secrets external-secrets/external-secrets -n external-secrets --create-namespace`
1. Create a service connection: `New-AzResourceGroupDeployment -ResourceGroupName speeltuin-jnb -TemplateFile ./jnb-aks-sc-keyvault.bicep -Confirm`
1. Create a secret store & service account: `kubectl apply -f keyvault-secret-store.yaml`
1. Link service account to managed identity with federated credentials: `New-AzResourceGroupDeployment -ResourceGroupName MC_speeltuin-jnb_jnb-aks_westeurope -TemplateFile ./jnb-aks-mi-keyvault.bicep -TemplateParameterFile ./jnb-aks-mi-keyvault.bicepparam -Confirm`
1. Create secret in Key Vault: `test-secret-1`
1. Create certificate in Key Vault: `test-certificate1`
1. Create the external secrets:
   - `kubectl apply -f jnb-keyvault-external-secrets.yaml`
   - `kubectl apply -f jnb-keyvault-certs-external-secrets.yaml`

### reloader

1. Install `reloader` using the helm chart: `helm install reloader stakater/reloader -n reloader --create-namespace`
