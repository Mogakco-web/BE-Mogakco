package project.mogakco.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	private static final String API_NAME = "모각코 서비스 API 명세서";
	private static final String API_VERSION = "v.0.1";
	private static final String API_DESCRIPTION = "Mogakco API Request Spec";


	@Bean
	public Docket swaggerAPI(){
		return new Docket(DocumentationType.OAS_30)
				.useDefaultResponseMessages(true)
				.select()
				.apis(RequestHandlerSelectors.basePackage("project.mogakco"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(API_NAME)
				.description(API_DESCRIPTION)
				.version(API_VERSION)
				.build();
	}

}