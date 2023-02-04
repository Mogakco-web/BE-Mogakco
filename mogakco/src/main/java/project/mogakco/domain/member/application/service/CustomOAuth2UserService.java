package project.mogakco.domain.member.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.mogakco.domain.member.entity.member.AuthProvider;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(kakao, google, naver)에서 가져온 유저 정보를 담고있음

		String registrationId = userRequest.getClientRegistration()
				.getRegistrationId(); // OAuth 서비스 이름(ex. kakao, naver, google)
		String userNameAttributeName = userRequest.getClientRegistration()
				.getProviderDetails()
				.getUserInfoEndpoint()
				.getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값
		Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들

		OAuth2Attributes.extract(registrationId, attributes); // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌
		oAuth2UserInfo.setProvider(registrationId);
		MemberSocial memberSocial = saveOrUpdate(oAuth2UserInfo);

		Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, oAuth2UserInfo, registrationId);

		return new DefaultOAuth2User(
				Collections.singleton(new SimpleGrantedAuthority("USER")),
				customAttribute,
				userNameAttributeName);

	}

	private Map customAttribute(Map attributes, String userNameAttributeName, OAuth2UserInfo oAuth2UserInfo, String registrationId) {
		Map<String, Object> customAttribute = new LinkedHashMap<>();
		customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
		customAttribute.put("provider", registrationId);
		customAttribute.put("name", oAuth2UserInfo.getName());
		customAttribute.put("email", oAuth2UserInfo.getEmail());
		return customAttribute;

	}

	private MemberSocial saveOrUpdate(OAuth2UserInfo oAuth2UserInfo) {

		MemberSocial member= memberRepository.findByEmailAndProvider(oAuth2UserInfo.getEmail(), AuthProvider.changeStringAuthProvider(oAuth2UserInfo.getProvider()))
				.map(memberSocial -> memberSocial.updateNewUserInfo(oAuth2UserInfo.getEmail(),oAuth2UserInfo.getName(),oAuth2UserInfo.getImageUrl()))
				.orElse(oAuth2UserInfo.toMember());
		return memberRepository.save(member);
	}



}