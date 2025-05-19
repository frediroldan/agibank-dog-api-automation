
# 🐶 Dog API Automation Project

Este projeto realiza testes automatizados na [Dog API](https://dog.ceo/dog-api/documentation) usando **Java 21**, **Cucumber**, **JUnit 5**, **RestAssured**, e **Jenkins**. Ele é capaz de validar endpoints REST, gerar relatórios de execução e ser executado em pipelines CI/CD.

---

## 📁 Estrutura do Projeto

```
dog-api-automation/
│
├── src/
│   └── test/
│       ├── java/
│       │   ├── runner/         # Classe TestRunner com JUnit 5 + tags
│       │   └── steps/          # Step definitions Cucumber
│       └── resources/
│           └── features/       # Cenários BDD em Gherkin
│
├── target/
│   ├── cucumber-reports/       # Relatórios personalizados
│   
│
├── Jenkinsfile                 # Pipeline declarativa CI/CD
├── pom.xml                     # Gerenciador de dependências Maven
└── README.md
```

---

## 🛠️ Tecnologias Utilizadas

- ✅ **Java 21**
- ✅ **JUnit Platform Suite 5**
- ✅ **Cucumber 7.14.0**
- ✅ **RestAssured 5.3.0**
- ✅ **Log4j 2**
- ✅ **Jenkins 2.510+**

---

## 🚀 Executando os Testes

### Via terminal (Maven)

```bash
mvn clean verify
```

### Executando por tag

```bash
mvn test -Dcucumber.filter.tags="Regressivo"
```

---

## 🧪 TestRunner - Configuração

```java
@Suite
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-report.json, html:target/cucumber-report.html")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
@IncludeTags("@Regressivo")
public class TestRunner {}
```

---

## 📊 Relatórios Gerados

- `target/cucumber-report.html` → HTML padrão do Cucumber
- `target/cucumber-html-reports/` → HTML customizado via Maven Plugin

---

## ⚙️ Jenkins CI - Pipeline Declarativa

- Clone do projeto via Git: https://github.com/frediroldan/agibank-dog-api-automation.git
- Build e testes via Maven
- Relatórios JUnit  + Cucumber Reports

```groovy
pipeline {
    agent any

    tools {
        jdk 'JDK-21'         // Nome do JDK configurado no Jenkins
        maven 'Maven-3.9.5'  // Nome do Maven configurado no Jenkins
    }

    environment {
        CUCUMBER_REPORT = 'target'
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
                        archiveArtifacts artifacts: 'target/**', fingerprint: true
                        echo "📄 Cucumber HTML report arquivado com sucesso!"
                    } else {
                        echo "⚠️ Relatório HTML não encontrado em: ${reportPath}"
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
```

---

## ✅ Resultados de Testes

- 🟢 Raças retornadas com sucesso
- 🟢 Imagens de raças com URLs válidas
- 🟢 Respostas com status HTTP corretos
- 🟢 Imagens distintas e com formatos esperados
- 🔴 Tratamento de erros em endpoints inválidos
- 🔄 Testes randomizados com persistência de imagens

---

## 📎 Observações Finais

- Pipeline ajustada para execução em **Windows** e **Unix/Linux** via Stage da Pipeline.
- Relatórios funcionais com **Cucumber** Report.



