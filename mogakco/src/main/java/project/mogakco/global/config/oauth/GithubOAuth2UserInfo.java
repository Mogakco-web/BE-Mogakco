package project.mogakco.global.config.oauth;

import lombok.Getter;
import project.mogakco.domain.member.entity.member.AuthProvider;
import project.mogakco.domain.member.entity.member.MemberRole;
import project.mogakco.domain.member.entity.member.MemberSocial;


@Getter
public class GithubOAuth2UserInfo{

	private String login;
	private String email;
	private String avatar_url;
	private String repos_url;
	private AuthProvider authProvider;

	public GithubOAuth2UserInfo(String login, String email, String avatar_url, String repos_url,AuthProvider authProvider) {
		this.login = login;
		this.email = email;
		this.avatar_url = avatar_url;
		this.repos_url = repos_url;
		this.authProvider=authProvider;
	}

	public MemberSocial toEntity(){
		return MemberSocial.builder()
				.email(email)
				.nickname(login)
				.imgUrl(avatar_url)
				.authProvider(authProvider)
				.role(MemberRole.MEMBER)
				.gitRepoUrl(repos_url)
				.build();
	}
}