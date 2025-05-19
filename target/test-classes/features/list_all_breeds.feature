#language: pt

#Autor: Fredi Roldan
#Data: 15/05/2025

@ListAll @Regressivo
Funcionalidade: Listar todas as racas

  Cenario: Validar retorno de todas as racas de cães da Dog API
    Dado que gero a lista mais atual de racas da API
    Quando envio uma requisicao GET para "/breeds/list/all"
    E a resposta deve conter todas as racas esperadas
    Entao o status da resposta deve ser 200

  Cenario: Validar que o corpo da resposta contém o campo "message"
    Quando envio uma requisicao GET para "/breeds/list/all"
    Entao o corpo da resposta deve conter o campo "message"

  Cenario: Validar que o campo status do JSON é "success"
    Quando envio uma requisicao GET para "/breeds/list/all"
    Entao o campo "status" da resposta deve ser "success"

  Cenario: Validar que pelo menos uma raca é retornada
    Quando envio uma requisicao GET para "/breeds/list/all"
    Entao deve existir pelo menos uma raça na lista retornada

  Cenario: requisicao com endpoint incorreto deve retornar erro 404
    Quando envio uma requisicao GET para "/breed/list/all"
    Entao o status da resposta deve ser 404