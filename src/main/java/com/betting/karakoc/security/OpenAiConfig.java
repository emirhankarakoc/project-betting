package com.betting.karakoc.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "GITHUB",
                        url = "https://github.com/emirhankarakoc/betting"
                ),
                description = "the below links opens github repo",
                title = "Use project with documentation. github repo -> readme.md",
                version = "1.0",
                license = @License(
                        name = "KARAKOC",
                        url = "https://github.com/emirhankarakoc/betting"
                ),
                termsOfService = "https://github.com/emirhankarakoc/betting"
        )

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenAiConfig {
}

