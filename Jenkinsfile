pipeline {
    agent any

    tools {
        jdk 'JDK-21'         // Nome do JDK configurado no Jenkins
        maven 'Maven-3.9.5'  // Nome do Maven configurado no Jenkins
    }

    environment {
        ALLURE_RESULTS = 'target/allure-results'
        CUCUMBER_REPORT = 'target/cucumber-html-reports'
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
                sh 'ls -lah target'
              } else {
                bat 'mvn clean verify'
                bat 'dir target'
              }
            }
          }
        }

        stage('Publicar Relatórios') {
            steps {
                script {
                    junit 'target/surefire-reports/*.xml'

                    def reportPath = "${env.CUCUMBER_REPORT}"
                    def exists = fileExists(reportPath)
                    if (exists) {
                        archiveArtifacts artifacts: "${reportPath}/* ", fingerprint: true
                        echo "📄 Cucumber HTML report arquivado com sucesso!"
                    } else {
                        echo "⚠️ Relatório HTML não encontrado em: ${reportPath}"
                    }
                }
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    def exists = fileExists(env.ALLURE_RESULTS)
                    if (exists) {
                        allure includeProperties: false, jdk: 'JDK-21', results: [[path: "${env.ALLURE_RESULTS}"]]
                        echo "📊 Relatório Allure gerado com sucesso!"
                    } else {
                        echo "⚠️ Pasta de resultados Allure não encontrada: ${env.ALLURE_RESULTS}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo '🔚 Pipeline finalizada'
        }
        success {
            echo '✅ Pipeline executada com sucesso!'
        }
        failure {
            echo '❌ Falha na execução da pipeline.'
        }
    }
}
