package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.E;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DogApiSteps {

    private Response response;
    private List<String> imagens;
    private static final Logger logger = LogManager.getLogger(DogApiSteps.class);

    // Realiza requisição GET para o endpoint informado
    @Quando("envio uma requisicao GET para {string}")
    public void envioUmaRequisicaoGetPara(String endpoint) {
        response = given()
                .baseUri("https://dog.ceo/api")
                .when()
                .get(endpoint);
    }

    // Valida qualquer status code retornado pela API
    @Então("o status da resposta deve ser {int}")
    public void statusRespostaDeveSer(int esperado) {
        int atual = response.getStatusCode();
        logger.info("✅ Validando status code: esperado = {}, recebido = {}", esperado, atual);
        logger.info("=========================================================");
        assertThat("Código de status retornado não corresponde ao esperado", atual, is(esperado));
    }

    // Gera e salva em arquivo a lista atual de todas as racas (incluindo sub-racas) da API
    @Dado("que gero a lista mais atual de racas da API")
    public void gerarListaAtualDeTodasRacas() throws IOException {
        Response apiResponse = given()
                .baseUri("https://dog.ceo/api")
                .when()
                .get("/breeds/list/all");

        Map<String, List<String>> breeds = apiResponse.jsonPath().getMap("message");
        List<String> lista = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : breeds.entrySet()) {
            if (entry.getValue().isEmpty()) {
                lista.add(entry.getKey());
            } else {
                for (String sub : entry.getValue()) {
                    lista.add(entry.getKey() + "/" + sub);
                }
            }
        }

        Collections.sort(lista);
        Path caminho = Paths.get("src/test/resources/data/listaTodasRacas.txt");
        Files.write(caminho, lista, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // Verifica se o JSON de resposta contém todas as racas esperadas
    @E("a resposta deve conter todas as racas esperadas")
    public void respostaDeveConterTodasRacas() throws IOException {
        List<String> expected = Files.readAllLines(Paths.get("src/test/resources/data/listaTodasRacas.txt"));
        Map<String, List<String>> responseMap = response.jsonPath().getMap("message");
        Set<String> actualBreeds = new HashSet<>();

        for (Map.Entry<String, List<String>> entry : responseMap.entrySet()) {
            String breed = entry.getKey();
            List<String> subBreeds = entry.getValue();
            if (subBreeds == null || subBreeds.isEmpty()) {
                actualBreeds.add(breed);
            } else {
                for (String sub : subBreeds) {
                    actualBreeds.add(breed + "/" + sub);
                }
            }
        }

        assertThat("Racas retornadas são diferentes das esperadas",
                actualBreeds, containsInAnyOrder(expected.toArray()));
    }

    // Assegura que o corpo da resposta contenha determinado campo
    @Então("o corpo da resposta deve conter o campo {string}")
    public void corpoDaRespostaDeveConterCampo(String campo) {
        boolean contem = response.getBody().jsonPath().get(campo) != null;
        logger.info("Verificando se o campo '{}' está presente na resposta: {}", campo, contem);
        assertThat("Campo esperado não encontrado: " + campo, contem, is(true));
    }

    // Valida o valor de um campo específico na resposta JSON
    @Então("o campo {string} da resposta deve ser {string}")
    public void campoDaRespostaDeveSer(String campo, String valorEsperado) {
        String valor = response.jsonPath().getString(campo);
        logger.info("Campo '{}' retornado: '{}'", campo, valor);
        assertThat("Valor inesperado para o campo '" + campo + "'",
                valor, is(equalTo(valorEsperado)));
    }

    // Confirma que existe ao menos uma raça no objeto "message"
    @Então("deve existir pelo menos uma raça na lista retornada")
    public void deveConterPeloMenosUmaRaca() {
        Map<String, List<String>> racas = response.jsonPath().getMap("message");
        int total = racas.size();
        logger.info("Quantidade de racas principais retornadas: {}", total);
        assertThat("Nenhuma raça foi retornada", total, greaterThan(0));
    }

    // Valida que cada URL no array "message" começa com https:// e termina em .jpg/.jpeg/.png
    @E("o corpo da resposta deve conter imagens por racas com URLs validas")
    public void corpoDeveConterUrlsValidas() {
        List<String> imagens = response.jsonPath().getList("message");
        assertThat(imagens, is(not(empty())));
        for (String url : imagens) {
            assertThat(url, startsWith("https://"));
            assertThat(url.toLowerCase(),
                    anyOf(endsWith(".jpg"), endsWith(".jpeg"), endsWith(".png")));
        }
    }

    // Valida uma única URL de imagem aleatória e grava em arquivo com timestamp
    @E("o corpo da resposta deve conter uma URL de imagem válida")
    public void corpoRespostaDeveConterUmaUrlValida() throws IOException {
        String imageUrl = response.jsonPath().getString("message");
        assertThat("A URL deve começar com https://", imageUrl, startsWith("https://"));
        assertThat("A URL deve terminar com .jpg, .jpeg ou .png",
                imageUrl.toLowerCase(), anyOf(endsWith(".jpg"), endsWith(".jpeg"), endsWith(".png")));

        // Salvar a URL em arquivo com timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String linha = timestamp + " - " + imageUrl + System.lineSeparator();

        Files.write(
                Paths.get("src/test/resources/images/display-single-random-images.txt"),
                linha.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

        logger.info("DISPLAY SINGLE RANDOM IMAGE");
        logger.info("Imagem validada e salva: {}", imageUrl);
        logger.info("ℹ️ Path: src/test/resources/images");
    }

    // Verifica se o JSON "message" contém exatamente N URLs
    @Então("a resposta deve conter exatamente {int} URLs")
    public void respostaDeveConterUrls(int quantidadeEsperada) throws IOException {
        imagens = response.jsonPath().getList("message");
        logger.info("DISPLAY MULTIPLE RANDOM IMAGES");
        logger.info("Quantidade de imagens retornadas: {}", imagens.size());
        assertThat("Número de imagens diferente do esperado", imagens.size(), is(quantidadeEsperada));

        Path pasta = Paths.get("src/test/resources/images");
        if (!Files.exists(pasta)) {
            Files.createDirectories(pasta);
            logger.info("Diretório criado: {}", pasta.toAbsolutePath());
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        StringBuilder sb = new StringBuilder();
        sb.append("Registro: ").append(timestamp).append(System.lineSeparator());
        for (String url : imagens) {
            sb.append(url).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        Files.write(
                pasta.resolve("display-multiple-random-images.txt"),
                sb.toString().getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND
        );
        logger.info("Imagens validadas e salvas: {}", imagens);
        logger.info("ℹ️ Path: src/test/resources/images");
    }

    // Confirma que cada URL do array é válida e exibe no log
    @Então("todas as URLs retornadas devem ser válidas")
    public void todasUrlsDevemSerValidas() {
        List<String> imagens = response.jsonPath().getList("message");
        for (String url : imagens) {
            //logger.info("URL Válidas recebida: {}", url);
            assertThat("A URL deve começar com https://", url, startsWith("https://"));
            assertThat("A URL deve terminar com .jpg, .jpeg, .png ou .webp",
                    url.toLowerCase(), matchesPattern(".*\\.(jpg|jpeg|png|webp)$"));
        }
    }

    // Assegura que as URLs retornadas sejam únicas (sem duplicatas)
    @Então("as URLs retornadas devem ser distintas")
    public void urlsDevemSerDistintas() {
        List<String> imagens = response.jsonPath().getList("message");
        Set<String> unicas = new HashSet<>(imagens);
        assertThat("As URLs não são únicas", unicas.size(), is(imagens.size()));
    }

}