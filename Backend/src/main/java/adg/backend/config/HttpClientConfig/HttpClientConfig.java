package adg.backend.config.HttpClientConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpClientConfig {
    @Bean
    WebClient recommenderClient() {
        return WebClient.builder()
                .baseUrl("http://python-scraper:8001")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
