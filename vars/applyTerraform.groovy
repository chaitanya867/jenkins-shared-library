def call(cloudProvider, dirName, awsCreds, azureCreds) {
    if (cloudProvider == 'AWS') {
        withCredentials([[
            $class: 'AmazonWebServicesCredentialsBinding',
            credentialsId: awsCreds,
            accessKeyVariable: 'AWS_ACCESS_KEY_ID',
            secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
        ]]) {
            dir(dirName) {
                sh 'terraform apply -input=false tfplan'
            }
        }
    } else if (cloudProvider == 'AZURE') {
        withCredentials([[ 
            $class: 'AzureServicePrincipal',
            credentialsId: azureCreds,
            subscriptionIdVariable: 'AZURE_SUBSCRIPTION_ID',
            clientIdVariable: 'AZURE_CLIENT_ID',
            clientSecretVariable: 'AZURE_CLIENT_SECRET',
            tenantIdVariable: 'AZURE_TENANT_ID'
        ]]) {
            dir(dirName) {
                sh 'terraform apply -input=false tfplan'
            }
        }
    }
}
