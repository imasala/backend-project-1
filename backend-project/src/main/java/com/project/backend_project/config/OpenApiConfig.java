package com.project.backend_project.config;

import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
            contact = @Contact(
                name = "Issa",
                email = "imasala@mhbbank.co.tz"
            ), 
            description = "OpenAPI documentation for Spring Security",
            title = "OpenAPI Specification - Issa",
            version = "1.0",
            license = @License(
                name = "License name",
                url = "https://example-url.com"
            ), 
            termsOfService = "Terms of Service"
    ), 
    servers = {
        @Server(
            description = "Local ENV",
            url = "http://localhost:8080"
        )

    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "Jwt auth description",
    scheme = "bearer", 
    type = SecuritySchemeType.HTTP, 
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
    
}
