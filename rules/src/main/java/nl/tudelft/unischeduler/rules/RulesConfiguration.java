package nl.tudelft.unischeduler.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.unischeduler.rules.core.RulesModule;
import nl.tudelft.unischeduler.rules.storing.RulesParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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

    public static RulesModule getDefaultRulesModule() {
        return defaultRulesModule;
    }

    public static void setDefaultRulesModule(RulesModule defaultRulesModule) {
        RulesConfiguration.defaultRulesModule = defaultRulesModule;
    }



    private static ObjectMapper generateDefaultObjectMapper() {
        return new ObjectMapper();
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return defaultObjectMapper;
    }

    public static void setDefaultObjectMapper(ObjectMapper defaultObjectMapper) {
        RulesConfiguration.defaultObjectMapper = defaultObjectMapper;
    }

    public static RulesParser getDefaultRulesParser() {
        return defaultRulesParser;
    }

    public static void setDefaultRulesParser(RulesParser defaultRulesParser) {
        RulesConfiguration.defaultRulesParser = defaultRulesParser;
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
