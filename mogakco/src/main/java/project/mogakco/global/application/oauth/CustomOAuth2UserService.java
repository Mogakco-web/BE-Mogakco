package project.mogakco.global.application.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;
import project.mogakco.global.config.oauth.GithubOAuth2UserInfo;
import project.mogakco.global.domain.oauth.OAuthAttributes;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final MemberRepository memberRepository;


	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration()
				.getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
				.getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		GithubOAuth2UserInfo githubOAuth2UserInfo = OAuthAttributes.extract(registrationId, attributes);

		MemberSocial member = saveOrUpdate(githubOAuth2UserInfo); // DB에 저장

		return new DefaultOAuth2User(
				Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString())),
				attributes,
				userNameAttributeName);
	}

	private MemberSocial saveOrUpdate(GithubOAuth2UserInfo userProfile) {
		MemberSocial member= memberRepository.findByEmailAndAuthProvider(userProfile.getEmail(), userProfile.getAuthProvider())
				.map(memberSocial -> memberSocial.updateNewUserInfo(userProfile.getAvatar_url(),userProfile.getEmail()))
				.orElse(userProfile.toEntity());
		return memberRepository.save(member);
	}

}