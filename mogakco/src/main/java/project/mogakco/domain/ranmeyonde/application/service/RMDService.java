package project.mogakco.domain.ranmeyonde.application.service;

import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface RMDService {

	public ResponseEntity<?> getBackendQuestion() throws URISyntaxException;
}
