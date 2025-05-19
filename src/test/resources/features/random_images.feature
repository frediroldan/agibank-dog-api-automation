#language: pt

#Autor: Fredi Roldan
#Data: 15/05/2025

@RandomImages #@Regressivo
Funcionalidade: Buscar múltiplas imagens aleatórias de cães

  #Display single random image
  Cenário: Validar imagens retornadas aleatoriamente
    Quando envio uma requisição GET para "/breeds/image/random"
    E o corpo da resposta deve conter uma URL de imagem válida
    Então o status da resposta deve ser 200

  #Display multiple random images
  Cenário: Validar que são retornadas exatamente 3 imagens
    Quando envio uma requisição GET para "/breeds/image/random/3"
    E a resposta deve conter exatamente 3 URLs
    Então o status da resposta deve ser 200

  Cenário: Requisição com valor não numérico retorna imagem padrão
    Quando envio uma requisição GET para "/breeds/image/random/abc"
    E a resposta deve conter exatamente 1 URLs
    Então o status da resposta deve ser 200

  Cenário: Requisição com número negativo retorna imagem padrão
    Quando envio uma requisição GET para "/breeds/image/random/-1"
    E a resposta deve conter exatamente 1 URLs
    Então o status da resposta deve ser 200

  Cenário: Validar que todas as imagens possuem URLs válidas
    Quando envio uma requisição GET para "/breeds/image/random/3"
    E todas as URLs retornadas devem ser válidas
    Então o status da resposta deve ser 200

  Cenário: Validar unicidade das imagens retornadas
    Quando envio uma requisição GET para "/breeds/image/random/3"
    E as URLs retornadas devem ser distintas
    Então o status da resposta deve ser 200

  Cenário: Validar que o endpoint retorna status 200
    Quando envio uma requisição GET para "/breeds/image/random/3"
    Então o status da resposta deve ser 200

  Cenário: Requisição para endpoint incorreto deve retornar erro 404
    Quando envio uma requisição GET para "/breed/image/random/3"
    Então o status da resposta deve ser 404