package project.mogakco.domain.member.application.service;


import project.mogakco.domain.member.dto.GitHubResponseDTO;
import project.mogakco.domain.member.entity.member.MemberSocial;

import java.io.IOException;

public interface GithubSocialService {
	MemberSocial getAccessToken(String code) throws IOException;
}
