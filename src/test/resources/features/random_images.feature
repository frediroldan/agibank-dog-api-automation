#language: pt

#Autor: Fredi Roldan
#Data: 15/05/2025

@RandomImages @Regressivo
Funcionalidade: Buscar múltiplas imagens aleatórias de cães

  #Display single random image
  Cenario: Validar imagens retornadas aleatoriamente
    Quando envio uma requisicao GET para "/breeds/image/random"
    E o corpo da resposta deve conter uma URL de imagem válida
    Então o status da resposta deve ser 200

  #Display multiple random images
  Cenario: Validar que são retornadas exatamente 3 imagens
    Quando envio uma requisicao GET para "/breeds/image/random/3"
    E a resposta deve conter exatamente 3 URLs
    Então o status da resposta deve ser 200

  Cenario: requisicao com valor não numérico retorna imagem padrão
    Quando envio uma requisicao GET para "/breeds/image/random/abc"
    E a resposta deve conter exatamente 1 URLs
    Então o status da resposta deve ser 200

  Cenario: requisicao com número negativo retorna imagem padrão
    Quando envio uma requisicao GET para "/breeds/image/random/-1"
    E a resposta deve conter exatamente 1 URLs
    Então o status da resposta deve ser 200

  Cenario: Validar que todas as imagens possuem URLs válidas
    Quando envio uma requisicao GET para "/breeds/image/random/3"
    E todas as URLs retornadas devem ser válidas
    Então o status da resposta deve ser 200

  Cenario: Validar unicidade das imagens retornadas
    Quando envio uma requisicao GET para "/breeds/image/random/3"
    E as URLs retornadas devem ser distintas
    Então o status da resposta deve ser 200

  Cenario: Validar que o endpoint retorna status 200
    Quando envio uma requisicao GET para "/breeds/image/random/3"
    Então o status da resposta deve ser 200

  Cenario: requisicao para endpoint incorreto deve retornar erro 404
    Quando envio uma requisicao GET para "/breed/image/random/3"
    Então o status da resposta deve ser 404