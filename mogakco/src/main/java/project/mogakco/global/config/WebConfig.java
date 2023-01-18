package project.mogakco.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("/**")
				.allowedHeaders("Access-Control-Allow-Origin")
				.exposedHeaders("Access-Control-Allow-Origin")
				.allowCredentials(false).maxAge(3600)
				.allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
	}
}
