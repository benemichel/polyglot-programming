package com.polyglot.demo.project;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ForwardedHeaderFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.Context;

@SpringBootApplication
public class PolyglotDemoProject {

    static {
		SpringDocUtils.getConfig().addHiddenRestControllers(BasicErrorController.class);
	}

    public static void main (String[] args) {
        SpringApplication.run(PolyglotDemoProject.class);
    }

    @Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Store API").version("3")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}
}