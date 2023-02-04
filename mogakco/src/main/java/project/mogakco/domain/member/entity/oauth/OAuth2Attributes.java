package project.mogakco.domain.member.entity.oauth;


import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;


public enum OAuth2Attributes {
	GITHUB("github",(attributes)->{
		OAuth2UserInfo oAuth2UserInfo=new OAuth2UserInfo();
		oAuth2UserInfo.setName((String) attributes.get("login"));
		oAuth2UserInfo.setImageUrl((String)attributes.get("avatar_url"));
		oAuth2UserInfo.setEmail((String) attributes.get("email"));
		return oAuth2UserInfo;
	});



	private final String registrationId;
	private final Function<Map<String, Object>, OAuth2UserInfo> of;

	OAuth2Attributes(String registrationId, Function<Map<String, Object>, OAuth2UserInfo> of) {
		this.registrationId = registrationId;
		this.of = of;
	}

	public static OAuth2UserInfo extract(String registrationId, Map<String, Object> attributes) {
		return Arrays.stream(values())
				.filter(provider -> registrationId.equals(provider.registrationId))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new)
				.of.apply(attributes);
	}
}