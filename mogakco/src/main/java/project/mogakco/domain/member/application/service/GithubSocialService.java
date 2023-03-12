package project.mogakco.domain.member.application.service;

import project.mogakco.domain.member.dto.GitHubResponseDTO;

import java.io.IOException;

public interface GithubSocialService {
	String getAccessToken(String code) throws IOException;

	void access(String access_token) throws IOException;
	void logoutByDeleteToken(String git_authToken);
}
