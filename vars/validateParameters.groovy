def call(provider, repoUrl, branchName, dirName) {
    // Check if the cloud provider is valid
    if (!['AWS', 'AZURE'].contains(provider)) {
        error "Invalid CLOUD_PROVIDER in YAML. Must be either 'AWS' or 'AZURE'."
    }
    // Check for other required parameters
    if (!repoUrl) {
        error "Invalid REPO_URL in YAML."
    }
    if (!branchName) {
        error "Invalid BRANCH_NAME in YAML."
    }
    if (!dirName) {
        error "Invalid DIR_NAME in YAML."
    }
}
