package project.mogakco.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import project.mogakco.domain.member.entity.member.AuthProvider;
import project.mogakco.domain.member.entity.member.MemberRole;
import project.mogakco.domain.member.entity.member.MemberSocial;

@Getter
@Setter
public class GitHubResponseDTO {
	private String login;
	private String avatar_url;
	private String repos_url;
	private String email;

	public MemberSocial toEntity(){
		return MemberSocial.builder()
				.email(email)
				.gitRepoUrl(repos_url)
				.authProvider(AuthProvider.GITHUB)
				.role(MemberRole.MEMBER)
				.imgUrl(avatar_url)
				.nickname(login)
				.build();
	}
}
