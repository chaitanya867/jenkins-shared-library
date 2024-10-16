def call(dirName) {
    // Run security scan using Checkov
    dir(dirName) {
        sh 'checkov -d . || true'  // Continue even if Checkov finds issues
    }
}
