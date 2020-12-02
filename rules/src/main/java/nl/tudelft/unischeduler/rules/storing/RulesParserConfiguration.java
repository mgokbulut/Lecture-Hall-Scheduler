package nl.tudelft.unischeduler.rules.storing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RulesParserConfiguration {

    private static ObjectMapper defaultObjectMapper = generateDefaultObjectMapper();

    private static ObjectMapper generateDefaultObjectMapper() {
        return new ObjectMapper();
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return defaultObjectMapper;
    }

    public static void setDefaultObjectMapper(ObjectMapper defaultObjectMapper) {
        RulesParserConfiguration.defaultObjectMapper = defaultObjectMapper;
    }

    @Bean
    RulesParser rulesParser() {
        return new RulesParser(defaultObjectMapper);
    }
}
