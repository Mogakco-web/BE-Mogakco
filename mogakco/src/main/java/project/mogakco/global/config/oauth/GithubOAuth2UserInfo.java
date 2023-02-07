package project.mogakco.global.config.oauth;

import lombok.Getter;
import project.mogakco.domain.member.entity.MemberSocial;


@Getter
public class GithubOAuth2UserInfo{

	private String login;
	private String email;
	private String avatar_url;
	private String repos_url;

	public GithubOAuth2UserInfo(String login, String email, String avatar_url, String repos_url) {
		this.login = login;
		this.email = email;
		this.avatar_url = avatar_url;
		this.repos_url = repos_url;
	}

}