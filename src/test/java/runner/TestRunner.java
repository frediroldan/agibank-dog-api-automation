package runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Diretório onde estão seus arquivos .feature
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps") // Pacote com suas StepDefinitions
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-report.json, html:target/cucumber-report.html")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
@IncludeTags("Regressivo") // Troque "tag" pela tag desejada ao rodar
public class TestRunner {
}