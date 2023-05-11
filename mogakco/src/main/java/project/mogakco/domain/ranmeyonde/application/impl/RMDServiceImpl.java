package project.mogakco.domain.ranmeyonde.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import project.mogakco.domain.ranmeyonde.application.service.RMDService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class RMDServiceImpl implements RMDService {

	public static final String organization="Mogakco-web";
	public static final String repo="RanMyeonDe_List";

	@Override
	public ResponseEntity<?> getBackendQuestion() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.github.com/orgs/"+organization+"/teams";
		URI uri = new URI(url);
		Map<String, String> params = new HashMap<>();
		params.put("organization", organization);
		params.put("Authorization","Bearer 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");

		ResponseEntity<String> response = restTemplate.getForEntity(uri.toString(), String.class, params);
		String responseBody = response.getBody();
		System.out.println(responseBody);
		return null;
	}
}
