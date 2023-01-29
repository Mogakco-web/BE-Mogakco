package project.mogakco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MogakcoApplication {


	public static void main(String[] args) {
		SpringApplication.run(MogakcoApplication.class,args);
	}

}
