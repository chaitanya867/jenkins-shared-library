def call(dirName) {
    dir(dirName) {
        sh 'terraform plan -var-file=vm.tfvars -out=tfplan'
    }
}
