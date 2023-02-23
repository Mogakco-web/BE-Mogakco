package project.mogakco.domain.member.application.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import project.mogakco.domain.member.application.service.GithubSocialService;
import project.mogakco.domain.member.dto.GitHubResponseDTO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Service
@Log4j2
public class GithubSocialServiceImpl implements GithubSocialService {

	@Value("${spring.security.oauth2.client.registration.github.client-id}")
	private String client_id;
	@Value("${spring.security.oauth2.client.registration.github.client-secret}")
	private String client_secret;

	@Override
	public String getAccessToken(String code) throws IOException {
		URL url = new URL("https://github.com/login/oauth/access_token");
		System.out.println("code="+code);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept", "application/json");
//		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");

		// 이 부분에 client_id, client_secret, code를 넣어주자.
		// 여기서 사용한 secret 값은 사용 후 바로 삭제하였다.
		// 실제 서비스나 깃허브에 올릴 때 이 부분은 항상 주의하자.
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
			bw.write("client_id="+client_id+"&client_secret="+ client_secret +"&code=" + code);
			bw.flush();
		}

		int responseCode = conn.getResponseCode();
		String responseData = getResponse(conn, responseCode);
		System.out.println("responseCode="+responseCode);
		System.out.println("responseData="+responseData);

		conn.disconnect();
		/*System.out.println("responseData="+responseData);
		JsonParser jsonParser=new JsonParser();
		Object obj = jsonParser.parse(responseData);
		JSONObject jso = (JSONObject) obj;
		String authtoken = (String) jso.get("accessToken");
		return authtoken;*/
		return null;
	}

	@SneakyThrows
	@Override
	public void logoutByDeleteToken(String git_authToken){
		System.out.println("Git AuthToken="+git_authToken);
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic " + Base64Utils.encodeToString((client_id + ":" + client_secret).getBytes()));
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("access_token", git_authToken);

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Void> response = restTemplate.exchange(
				"https://api.github.com/applications/"+client_id+"/token",
				HttpMethod.DELETE,
				requestEntity,
				Void.class,
				client_id
		);

		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
			// 로그아웃이 성공한 경우 처리할 작업
		}
	}
	@Override
	public void access(String access_token) throws IOException{
		/*ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.readValue(responseData, Map.class);
		String access_token = map.get("access_token");*/

		URL url = new URL("https://api.github.com/user");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
		conn.setRequestProperty("Authorization", "token " + access_token);

		int responseCode = conn.getResponseCode();

		String res_data = getResponse(conn, responseCode);

		conn.disconnect();
		ObjectMapper objectMapper=new ObjectMapper();
		Map<String,String> result=objectMapper.readValue(res_data,Map.class);
		System.out.println("result="+result);

//		return initializeUserInfo(result);
	}

	private String getResponse(HttpURLConnection conn, int responseCode) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (responseCode == 200) {
			try (InputStream is = conn.getInputStream();
				 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					System.out.println("line="+line);
					sb.append(line);
				}
			}
		}
		return sb.toString();
	}

	private GitHubResponseDTO initializeUserInfo(Map<String,String> result){
		GitHubResponseDTO gitHubResponseDTO=new GitHubResponseDTO();
		gitHubResponseDTO.setLogin(result.get("login"));
		gitHubResponseDTO.setAvatar_url(result.get("avatar_url"));
		gitHubResponseDTO.setId(result.get("id"));
		gitHubResponseDTO.setName(result.get("name"));
		gitHubResponseDTO.setCreated_at(result.get("created_at"));
		gitHubResponseDTO.setRepo_url(result.get("repo_url"));
		gitHubResponseDTO.setNode_id(result.get("node_id"));
		gitHubResponseDTO.setType(result.get("type"));
		gitHubResponseDTO.setSite_admin(result.get("site_admin"));
		gitHubResponseDTO.setUpdated_at(result.get("updated_at"));

		return gitHubResponseDTO;
	}


}
