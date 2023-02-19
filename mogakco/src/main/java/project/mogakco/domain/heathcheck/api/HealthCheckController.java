package project.mogakco.domain.heathcheck.api;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class HealthCheckController {

	@PostMapping("/api/v1/healthcheck")
	public ResponseEntity<String> serverHealthCheckAPI(){
		log.info("ping-pong");
		return new ResponseEntity<>("PingPong", HttpStatus.OK);
	}
}
