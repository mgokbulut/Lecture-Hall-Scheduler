package nl.tudelft.unischeduler.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Data
@AllArgsConstructor
public class RulesConfiguration {

    private static ObjectMapper defaultObjectMapper = generateDefaultObjectMapper();
    private static RulesModule defaultRulesModule = generateDefaultRulesModule();
    private static RulesParser defaultRulesParser = generateDefaultRulesParser();

    private static RulesParser generateDefaultRulesParser() {
        return new RulesParser(defaultObjectMapper);
    }

    private static RulesModule generateDefaultRulesModule() {
        return new RulesModule();
    }

    private static ObjectMapper generateDefaultObjectMapper() {
        return new ObjectMapper();
    }


    @Bean
    RulesParser rulesParser() {
        return defaultRulesParser;
    }

    @Bean
    RulesModule rulesModule() {
        return defaultRulesModule;
    }

    @Bean
    ObjectMapper objectMapper() {
        return defaultObjectMapper;
    }

    @Bean
    WebClient databaseWebClient(@Autowired WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://database-service/").build();
    }
}
