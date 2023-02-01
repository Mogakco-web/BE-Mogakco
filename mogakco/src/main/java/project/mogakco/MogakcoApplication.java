package project.mogakco;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MogakcoApplication {
	/*@Value("${jdbc-url}")
	private String test;

	@PostConstruct
	public void test(){
		System.out.println(">>>>" +test);
	}*/

	@PostConstruct
	public void init(){
		System.out.println(">>> Server Open");
	}

	public static void main(String[] args) {
		SpringApplication.run(MogakcoApplication.class, args);
	}

}
