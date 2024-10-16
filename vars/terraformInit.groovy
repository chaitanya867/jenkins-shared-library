def call(dirName) {
    dir(dirName) {
        sh 'terraform init -upgrade'
    }
}
