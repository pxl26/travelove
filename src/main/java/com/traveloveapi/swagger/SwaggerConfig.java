package com.traveloveapi.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("https://api.travelovecompany.com")))
                .info(new Info().title("TRAVELOVE")
                        .description("REST-API document for Travelove")
                        .contact(new Contact()
                                .email("travelovecompany@gmail.com")
                                .name("Phan Xuan Loc").url("https://www.facebook.com/pxl.26"))
                        .version("1.0.0"));

    }
}

