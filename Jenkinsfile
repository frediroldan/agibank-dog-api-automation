pipeline {
    agent any

    tools {
        jdk 'JDK-21'         // Nome do JDK configurado no Jenkins
        maven 'Maven-3.9.5'  // Nome do Maven configurado no Jenkins
    }

    stages {
            stage('Checkout') {
                steps {
                    git url: 'https://github.com/frediroldan/agibank-dog-api-automation.git', branch: 'main'
                }
            }

            stage('Build & Test') {
                steps {
                    script {
                        if (isUnix()) {
                            sh 'mvn clean verify'
                        } else {
                            bat 'mvn clean verify'
                        }
                    }
                }
            }

            stage('Publicar RelatÃ³rios') {
                steps {
                    junit 'target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/cucumber-html-reports/**', fingerprint: true
                }
            }

            stage('Allure Report') {
                steps {
                    allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
                }
            }

        }

        post {
            always {
                echo 'Pipeline finalizada'
            }
            success {
                echo 'âœ… Pipeline executada com sucesso!'
            }
            failure {
                echo 'âŒ Falha na execuÃ§Ã£o da pipeline.'
            }
        }
    }