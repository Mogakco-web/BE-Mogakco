package project.mogakco.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.member.application.service.GithubSocialService;
import project.mogakco.domain.member.dto.GitHubResponseDTO;
import project.mogakco.domain.member.entity.member.MemberSocial;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GitHubSocialController {

	private final GithubSocialService githubSocialService;

	@GetMapping("/authorization/github")
	public void githubLogin(@PathParam("code")String code) throws IOException {
		System.out.println("loginCode="+code);
		githubSocialService.getAccessToken(code);
	}

	@DeleteMapping("/eliminate/authToken")
	public void githubLogout(@PathParam("authToken")String authToken){
		githubSocialService.logoutByDeleteToken(authToken);
	}
}
