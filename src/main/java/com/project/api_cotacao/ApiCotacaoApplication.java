package com.project.api_cotacao;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Cotação-API",
				description = "API para câmbio e transferências monetárias",
				version = "1"
		)
)
public class ApiCotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCotacaoApplication.class, args);
	}

}
