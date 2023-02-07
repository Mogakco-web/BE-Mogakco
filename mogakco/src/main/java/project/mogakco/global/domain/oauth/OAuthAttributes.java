package project.mogakco.global.domain.oauth;

import project.mogakco.domain.member.entity.member.AuthProvider;
import project.mogakco.global.config.oauth.GithubOAuth2UserInfo;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
	GITHUB("github", (attributes) -> {
		return new GithubOAuth2UserInfo(
				(String) attributes.get("login"),
				(String) attributes.get("email"),
				(String) attributes.get("avatar_url"),
				(String) attributes.get("repos_url"),
				AuthProvider.GITHUB
		);
	});

	private final String registrationId;
	private final Function<Map<String, Object>, GithubOAuth2UserInfo> of;

	OAuthAttributes(String registrationId, Function<Map<String, Object>, GithubOAuth2UserInfo> of) {
		this.registrationId = registrationId;
		this.of = of;
	}

	public static GithubOAuth2UserInfo extract(String registrationId, Map<String, Object> attributes) {
		return Arrays.stream(values())
				.filter(provider -> registrationId.equals(provider.registrationId))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new)
				.of.apply(attributes);
	}
}
