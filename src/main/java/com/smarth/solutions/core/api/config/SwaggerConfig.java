package com.smarth.solutions.core.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(new Info().title("API Core Service").version("v0.0.1")
                      .description("API para Smart Core Service que maneja las suscripciones. "
                          + "En producción el Gateway valida el JWT e inyecta X-User-Id y X-User-Role; "
                          + "en local (sin Gateway) hay que setear esas cabeceras manualmente vía Authorize."))
      .addSecurityItem(new SecurityRequirement().addList("X-User-Id").addList("X-User-Role"))
      .components(new Components()
          .addSecuritySchemes("X-User-Id", new SecurityScheme()
              .type(SecurityScheme.Type.APIKEY)
              .in(SecurityScheme.In.HEADER)
              .name("X-User-Id"))
          .addSecuritySchemes("X-User-Role", new SecurityScheme()
              .type(SecurityScheme.Type.APIKEY)
              .in(SecurityScheme.In.HEADER)
              .name("X-User-Role")));
  }
}