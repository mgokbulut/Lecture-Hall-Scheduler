package nl.tudelft.unischeduler.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    public static ObjectMapper getDefaultObjectMapper() {
        return defaultObjectMapper;
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
}
