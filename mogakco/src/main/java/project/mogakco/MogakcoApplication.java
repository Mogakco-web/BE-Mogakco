package project.mogakco;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
public class MogakcoApplication {

	@PostConstruct
	public void init(){
		System.out.println(">>> Server Open");
	}

	public static void main(String[] args) {
		SpringApplication.run(MogakcoApplication.class,args);
	}

}
