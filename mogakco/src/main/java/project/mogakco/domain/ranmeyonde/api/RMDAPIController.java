package project.mogakco.domain.ranmeyonde.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.ranmeyonde.application.service.RMDService;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/rmd")
public class RMDAPIController {

	private final RMDService rmdService;

	@GetMapping("/getConnect")
	public void getRMDConnect() throws URISyntaxException {
		rmdService.getBackendQuestion();
	}
}
