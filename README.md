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
1. Create the namespace `poc-external-secrets`: `kubectl create ns poc-external-secrets`
1. Install using the helm chart: `helm install external-secrets external-secrets/external-secrets -n external-secrets --create-namespace`
1. Create a service connection: `New-AzResourceGroupDeployment -ResourceGroupName speeltuin-jnb -TemplateFile ./jnb-aks-sc-keyvault.bicep -Confirm`
1. Create a secret store & service account: `kubectl apply -f jnb-keyvault-secret-store.yaml`
   - Set the correct client id in the file `jnb-keyvault-secret-store.yaml`
1. Link service account to managed identity with federated credentials: `New-AzResourceGroupDeployment -ResourceGroupName MC_speeltuin-jnb_jnb-aks_westeurope -TemplateFile ./jnb-aks-mi-keyvault.bicep -TemplateParameterFile ./jnb-aks-mi-keyvault.bicepparam -Confirm`
   - Set the correct issuer URL in the file `jnb-aks-mi-keyvault.bicepparam`
1. Create secret in Key Vault: `test-secret-1`
1. Create certificate in Key Vault: `test-certificate1`
1. Create the external secrets:
   - `kubectl apply -f jnb-keyvault-secrets.yaml`
   - `kubectl apply -f jnb-keyvault-certs.yaml`

### reloader

1. Install `reloader` using the helm chart: `helm install reloader stakater/reloader -n reloader --create-namespace`

### demo-app

1. Build the `demo-app` project
1. Deploy it to Kubernetes: `kubectl apply -n poc-external-secrets -f demo-app/target/kubernetes/kubernetes.yml`
