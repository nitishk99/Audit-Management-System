package com.cts.checklist.swagger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * 
 * This class is used for the configuration and customization of
 * swagger. 
 * Swagger is used to describe and document RESTful APIs 
 * with Swagger user will able to see the end-points to access the
 * application.
 *
 *
 */
@Configuration
public class SwaggerConfig {
	@Autowired
	Environment env;
	
	/**
	 * Configure Swagger
	 * @return OpenApi which internally includes swagger
	 * 
	 * Customized Information -
	 * 	 Title for API
	 * 	 Description of API
	 * 	 Terms of Service Url
	 * 	 License
	 * 	 Contact Information
	 * 	 Version of API
	 * 		
	 */
	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title(env.getProperty("string.swagg.title"))
						.description(env.getProperty("string.swagg.desc"))
						.termsOfService(env.getProperty("string.swagg.help"))
						.version(env.getProperty("string.swagg.ver"))
						.contact(new Contact()
								.name(env.getProperty("conctact.name"))
								.url(env.getProperty("contact.web"))
								.email(env.getProperty("contact.email")))
						.license(new License()
								.name(env.getProperty("string.swagg.lic"))
								.url("http://springdoc.org")))
						.externalDocs(new ExternalDocumentation()
								.description("SpringShop Wiki Documentation")
								.url("https://springshop.wiki.github.org/docs"));
	}
}
