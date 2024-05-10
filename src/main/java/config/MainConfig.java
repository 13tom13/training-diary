package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = {"out", "utils", "entity"})
public class MainConfig {

    @Autowired
    Environment env;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
