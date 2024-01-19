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
                description = "alttaki tum url'ler github reposuna gider.",
                title = "GITHUBDAN - DOKUMANTASYONU - TAKIP - EDEREK - PROJEYI - KULLANIN",
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

