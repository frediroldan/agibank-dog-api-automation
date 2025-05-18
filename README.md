
# 🐶 Projeto de Testes Automatizados da API Dog CEO

Este projeto implementa testes automatizados usando **Cucumber**, **Java**, **RestAssured** e **JUnit**, para validar o endpoint `/api/breeds/list/all` da [Dog CEO API](https://dog.ceo/dog-api/), que retorna todas as raças e sub-raças de cachorro disponíveis.

---

## ✅ Objetivo

Validar se a API retorna corretamente todas as raças de cachorro, incluindo sub-raças, comparando com uma lista previamente definida em um DataTable Gherkin.

---

## 🧱 Tecnologias utilizadas

- **Java 11+**
- **Maven** (ou Gradle)
- **JUnit 4 ou 5**
- **Cucumber JVM**
- **RestAssured**
- **Hamcrest**

---

## 📁 Estrutura de pastas

```
src
├── test
│   ├── java
│   │   └── steps
│   │       └── DogApiSteps.java
│   └── resources
│       └── features
│           └── listar_racas.feature
```

---

## 🧪 Cenário de Teste

O cenário `Validar retorno de todas as raças de cachorro` executa os seguintes passos:

1. Envia uma requisição GET para `https://dog.ceo/api/breeds/list/all`
2. Valida que o status da resposta é `200`
3. Compara cada uma das raças e sub-raças retornadas com as do DataTable.

Exemplo de entrada no `listar_racas.feature`:

```gherkin
Cenário: Validar retorno de todas as raças de cachorro
  Quando envio uma requisição GET para "/api/breeds/list/all"
  Então o status da resposta deve ser 200
  E a resposta deve conter as seguintes raças:
    | raças             |
    | bulldog/english   |
    | poodle/miniature  |
    | akita             |
    | labrador          |
    | spaniel/cocker    |
    ...
```

---

## ▶️ Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/teste-api-dog-ceo.git
cd teste-api-dog-ceo
```

### 2. Instale as dependências (Maven)

```bash
mvn clean install
```

> Ou com Gradle:

```bash
./gradlew build
```

### 3. Execute os testes

```bash
mvn test
```

---

## 📦 Dependências principais (Maven)

```xml
<dependencies>
  <dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.14.0</version>
  </dependency>
  <dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.1</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

---

## 📝 Considerações

- O projeto pode ser complementado com testes negativos, testes por sub-raça, e testes de performance.

---

## 📌 Autor

Projeto criado por: Fredi Roldan  
📧 frediroldan@gmail.com
