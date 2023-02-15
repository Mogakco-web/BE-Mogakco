package project.mogakco.global.security.oauth2;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.mogakco.domain.member.dto.GitHubDTO;
import project.mogakco.domain.member.entity.member.AuthProvider;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;
import project.mogakco.global.exception.OAuth2AuthenticationProcessingException;
import project.mogakco.global.security.UserPrincipal;
import project.mogakco.global.security.oauth2.user.OAuth2UserInfo;
import project.mogakco.global.security.oauth2.user.OAuth2UserInfoFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<MemberSocial> userOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
        MemberSocial memberSocial;
        if(userOptional.isPresent()) {
            memberSocial = userOptional.get();
            if(!memberSocial.getAuthProvider().toString().equals(AuthProvider.valueOf((oAuth2UserRequest.getClientRegistration().getRegistrationId())))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        (memberSocial.getAuthProvider().toString() + " account. Please use your " + memberSocial.getAuthProvider().toString()  +
                        " account to login."));
            }
            memberSocial.updateNewUserInfo(oAuth2UserInfo.getEmail(),oAuth2UserInfo.getImageUrl());
        } else {
            memberSocial = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(memberSocial, oAuth2User.getAttributes());
    }

    private MemberSocial registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        GitHubDTO gitHubDTO=new GitHubDTO();
        gitHubDTO.setAuthProvider((oAuth2UserRequest.getClientRegistration().getRegistrationId().toLowerCase().equals("github")?AuthProvider.GITHUB:AuthProvider.GOOGLE));
        gitHubDTO.setLogin(oAuth2UserInfo.getName());
        gitHubDTO.setEmail(oAuth2UserInfo.getEmail());
        gitHubDTO.setAvatar_url(oAuth2UserInfo.getImageUrl());
        return memberRepository.save(gitHubDTO.toEntity());
    }

}
