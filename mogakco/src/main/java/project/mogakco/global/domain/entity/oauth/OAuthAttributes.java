package project.mogakco.global.domain.entity.oauth;

import lombok.Builder;
import lombok.Getter;
import project.mogakco.domain.member.entity.member.MemberRole;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.entity.member.SocialType;
import project.mogakco.global.domain.dto.oauth.userinfo.GithubOAuth2UserInfo;
import project.mogakco.global.domain.dto.oauth.userinfo.GoogleOAuth2UserInfo;
import project.mogakco.global.domain.dto.oauth.userinfo.OAuth2UserInfo;

import java.util.Map;

@Getter
public class OAuthAttributes {

	private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
	private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

	@Builder
	public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}


	public static OAuthAttributes of(SocialType socialType,
									 String userNameAttributeName, Map<String, Object> attributes) {
		if (socialType==SocialType.GITHUB){
			return ofGithub(userNameAttributeName,attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);
	}

	public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.nameAttributeKey(userNameAttributeName)
				.oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
				.build();
	}


	public static OAuthAttributes ofGithub(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
				.nameAttributeKey(userNameAttributeName)
				.oauth2UserInfo(new GithubOAuth2UserInfo(attributes))
				.build();
	}

	/**
	 * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
	 * OAuth2UserInfo에서 socialId(식별값), nickname, imageUrl을 가져와서 build
	 * email에는 UUID로 중복 없는 랜덤 값 생성
	 * role은 GUEST로 설정
	 */
	public MemberSocial toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo,String authToken) {
		return MemberSocial.builder()
				.socialType(socialType)
				.oauthId(oauth2UserInfo.getId())
				.email(oauth2UserInfo.getEmail())
				.nickname(oauth2UserInfo.getNickname())
				.member_imgUrl(oauth2UserInfo.getImageUrl())
				.authToken(authToken)
				.role(MemberRole.USER)
				.build();
	}
}