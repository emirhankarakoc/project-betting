package com.betting.karakoc.utils;

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
                        name = "KARAKOC",
                        email = "emirhankarakoc@yahoo.com"
                ),
                description = "betting",
                title = "Betting  -  KARAKOC",
                version = "1.2.3.4.5.6.7.8.9.10"),
        servers = {
                @Server(
                        description = "CLOUD ENV",
                        url = "https://bettting.ey.r.appspot.com"
                )
        }

)

public class OpenApiConfig {
}