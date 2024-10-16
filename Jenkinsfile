@Library('your-shared-library-name') _ // This line is only valid in a Groovy Jenkinsfile.

pipeline {
    agent any

    environment {
        AWS_CREDS = 'b838de0d-458a-45e3-9971-b0f708cc8ca8'
        AZURE_CREDS = 'your-azure-credentials-id'
    }

    stages {
        stage('Load Parameters') {
            steps {
                configFileProvider([configFile(fileId: 'pipeline-config.yml', variable: 'YAML_FILE')]) {
                    script {
                        def yamlContent = readFile("${YAML_FILE}")
                        def params = readYaml(text: yamlContent)

                        env.CLOUD_PROVIDER = params.CLOUD_PROVIDER
                        env.RUN_SECURITY_SCAN = params.RUN_SECURITY_SCAN.toString()
                        env.REPO_URL = params.REPO_URL
                        env.BRANCH_NAME = params.BRANCH_NAME
                        env.DIR_NAME = params.DIR_NAME
                    }
                }
            }
        }

        stage('Validate Parameters') {
            steps {
                script {
                    validateParameters(env.CLOUD_PROVIDER, env.REPO_URL, env.BRANCH_NAME, env.DIR_NAME)
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: env.BRANCH_NAME]], userRemoteConfigs: [[url: env.REPO_URL, credentialsId: 'your-git-credentials-id']]])
            }
        }

        stage('Security Scan') {
            when {
                expression { env.RUN_SECURITY_SCAN.toBoolean() }
            }
            steps {
                script {
                    runSecurityScan(env.DIR_NAME)
                }
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    terraformInit(env.DIR_NAME)
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    terraformPlan(env.DIR_NAME)
                }
            }
        }

        stage('Approval') {
            steps {
                input message: 'Do you want to apply the Terraform plan?', ok: 'Apply'
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    applyTerraform(env.CLOUD_PROVIDER, env.DIR_NAME, AWS_CREDS, AZURE_CREDS)
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
