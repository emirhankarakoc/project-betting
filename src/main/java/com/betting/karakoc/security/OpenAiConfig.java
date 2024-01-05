package com.betting.karakoc.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

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
        ),
        servers = {
                @Server(
                        description = "local",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "prod",
                        url = "https://bettting.ey.r.appspot.com/"
                )
        }

)

public class OpenAiConfig {
}

