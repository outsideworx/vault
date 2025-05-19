package application.configuration.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
class Properties {
    private Map<String, Client> clients;

    @Data
    static class Client {
        private String caller;
        private String origin;
        private String token;
    }
}