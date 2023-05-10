package project.mogakco.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("/**")
				.allowedOrigins("https://f206-114-205-30-236.ngrok-free.app`")
				.allowedOrigins("https://localhost:3000/**")
				.allowedOrigins("http://localhost:3000/**")
				.allowedOrigins("http://127.0.0.1:4040/**")
				.allowedHeaders("Access-Control-Allow-Origin")
				.exposedHeaders("Access-Control-Allow-Origin")
				.allowCredentials(true)
				.allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/docApi/v2/api-docs", "/v2/api-docs");
		registry.addRedirectViewController("/docApi/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
		registry.addRedirectViewController("/docApi/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
		registry.addRedirectViewController("/docApi/swagger-resources", "/swagger-resources");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry
				.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");

		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
