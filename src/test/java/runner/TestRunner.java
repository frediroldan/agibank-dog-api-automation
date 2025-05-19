package runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.*;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Diretório onde estão seus arquivos .feature
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps") // Pacote com suas StepDefinitions
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report.html, json:target/cucumber-report.json")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
// Para rodar por tags, defina aqui:
@IncludeTags("BreedImages") // Troque "tag" pela tag desejada ao rodar
public class TestRunner {
}