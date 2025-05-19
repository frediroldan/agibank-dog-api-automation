#language: pt

#Autor: Fredi Roldan
#Data: 15/05/2025

@BreedImages #@Regressivo
Funcionalidade: Validar imagens por raca

  Cenario: Validar imagens da raca hound-afghan
    Quando envio uma requisicao GET para "/breed/hound/afghan/images"
    E o corpo da resposta deve conter imagens por racas com URLs validas
    Entao o status da resposta deve ser 200

  Cenario: Validar imagens da raca hound-basset
    Quando envio uma requisicao GET para "/breed/hound/basset/images"
    E o corpo da resposta deve conter imagens por racas com URLs validas
    Entao o status da resposta deve ser 200

  Cenario: Validar imagens da raca hound-blood
    Quando envio uma requisicao GET para "/breed/hound/blood/images"
    E o corpo da resposta deve conter imagens por racas com URLs validas
    Entao o status da resposta deve ser 200

  Cenario: Validar imagens da raca hound-english
    Quando envio uma requisicao GET para "/breed/hound/english/images"
    E o corpo da resposta deve conter imagens por racas com URLs validas
    Entao o status da resposta deve ser 200

  Cenario: Validar imagens da raca hound-ibizan
    Quando envio uma requisicao GET para "/breed/hound/ibizan/images"
    E o corpo da resposta deve conter imagens por racas com URLs validas
    Entao o status da resposta deve ser 200

  Cenario: Validar imagens da raca hound-plott
    Quando envio uma requisicao GET para "/breed/hound/plott/images"
    E o corpo da resposta deve conter imagens por racas com URLs validas
    Entao o status da resposta deve ser 200

  Cenario: Validar imagens da raca hound-walker
    Quando envio uma requisicao GET para "/breed/hound/walker/images"
    E o corpo da resposta deve conter imagens por racas com URLs validas
    Entao o status da resposta deve ser 200

  Cenario: Validar imagens de raca inexistente
    Quando envio uma requisicao GET para "/breed/hound/banana/images"
    Entao o status da resposta deve ser 404

  Cenario: Validar imagens com estrutura de URL inválida
    Quando envio uma requisicao GET para "/breed/hound//images"
    Entao o status da resposta deve ser 404