#language: pt

#Autor: Fredi Roldan
#Data: 15/05/2025

@ListAll @Regressivo
Funcionalidade: Listar todas as raças

  Cenário: Validar retorno de todas as raças de cães da Dog API
    Dado que gero a lista mais atual de raças da API
    Quando envio uma requisição GET para "/breeds/list/all"
    E a resposta deve conter todas as raças esperadas
    Então o status da resposta deve ser 200

  Cenário: Validar que o corpo da resposta contém o campo "message"
    Quando envio uma requisição GET para "/breeds/list/all"
    Então o corpo da resposta deve conter o campo "message"

  Cenário: Validar que o campo status do JSON é "success"
    Quando envio uma requisição GET para "/breeds/list/all"
    Então o campo "status" da resposta deve ser "success"

  Cenário: Validar que pelo menos uma raça é retornada
    Quando envio uma requisição GET para "/breeds/list/all"
    Então deve existir pelo menos uma raça na lista retornada

  Cenário: Requisição com endpoint incorreto deve retornar erro 404
    Quando envio uma requisição GET para "/breed/list/all"
    Então o status da resposta deve ser 404