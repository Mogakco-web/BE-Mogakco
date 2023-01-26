package project.mogakco.domain.member.application.service;


import project.mogakco.domain.member.dto.GitHubResponseDTO;

import java.io.IOException;

public interface GithubSocialService {
	GitHubResponseDTO getAccessToken(String code) throws IOException;
}
