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
                        sh 'mvn clean install'
                    } else {
                        bat 'mvn clean install'
                    }
                }
            }
        }

       stage('Publicar Relatórios') {
                   steps {
                       junit 'target/surefire-reports/*.xml'
                   }
               }
       }

    post {
            always {
                echo 'Pipeline finalizada'
            }
            success {
                echo '✅ Pipeline executada com sucesso!'
            }
            failure {
                echo '❌ Falha na execução da pipeline.'
            }
        }
}