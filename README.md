#  poc-external-secrets

This PoC shows the usage of the [external-secrets](https://external-secrets.io/) operator to retrieve secrets and certificates from Azure Key Vault using a workload managed identity.

## Setup

1. Create the following using the repo [my-azure-env](http://github.com/ninckblokje/my-azure-env)
   - VNet
   - Key vault
   - AKS
2. Install using the helm chart: `helm install external-secrets external-secrets/external-secrets -n external-secrets --create-namespace`
3. Create a service connection: `New-AzResourceGroupDeployment -ResourceGroupName speeltuin-jnb -TemplateFile ./jnb-aks-sc-keyvault.bicep -Confirm`
4. Create a secret store & service account: `kubectl apply -f keyvault-secret-store.yaml`
5. Link service account to managed identity with federated credentials: `New-AzResourceGroupDeployment -ResourceGroupName MC_speeltuin-jnb_jnb-aks_westeurope -TemplateFile ./jnb-aks-mi-keyvault.bicep -TemplateParameterFile ./jnb-aks-mi-keyvault.bicepparam -Confirm`
6. Create secret in Key Vault: `test-secret-1`
7. Create certificate in Key Vault: `test-certificate1`
8. Create the external secrets:
   - `kubectl apply -f jnb-keyvault-external-secrets.yaml`
   - `kubectl apply -f jnb-keyvault-certs-external-secrets.yaml`
