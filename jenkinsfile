pipeline {
    agent any

    tools {
        jdk 'JDK-21'       // Nome do JDK configurado no Jenkins
        maven 'Maven-3.9.5'  // Nome do Maven configurado no Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://seu-repositorio.git', branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Publicar Relatórios') {
            steps {
                cucumber buildStatus: 'UNSTABLE',
                          fileIncludePattern: '**/cucumber.json'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            junit '**/target/surefire-reports/*.xml'
        }
    }
}

