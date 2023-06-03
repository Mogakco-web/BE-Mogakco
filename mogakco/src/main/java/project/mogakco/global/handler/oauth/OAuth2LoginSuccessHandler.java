package project.mogakco.global.handler.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.reward.RewardMemberSocialCheckService;
import project.mogakco.domain.mypage.application.service.reward.RewardService;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.global.application.fcm.service.FCMService;
import project.mogakco.global.application.jwt.JwtService;
import project.mogakco.global.config.FCMConfig;
import project.mogakco.global.domain.entity.oauth.CustomOAuth2User;
import project.mogakco.global.dto.oauth.OAuthDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtService;

//    private final UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.info("OAuth2 Login 성공!");
		log.info("callback-git url : "+request.getRequestURI());
		try {
			CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
			log.info("authentication:"+ oAuth2User);
			log.info("AUTH:"+authentication.toString());
			log.info("OAuth2User="+oAuth2User.getAttributes().get("login"));

			OAuthDTO.OAuthSuccess oAuthSuccess = loginSuccess(response, oAuth2User);// 로그인에 성공한 경우 access, refresh 토큰 생성
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("accessToken",oAuthSuccess.getAccessToken());
			response.setHeader("refreshToken",oAuthSuccess.getRefreshToken());
			response.setContentType("application/json;charset=UTF-8");
//			String responseBody = "{\"accessToken\": \"" + oAuthSuccess.getAccessToken() + "\", \"refreshToken\": \"" + oAuthSuccess.getRefreshToken() + "\"}";
//			response.getWriter().write(responseBody);
			response.sendRedirect("http://localhost:3000/callback");
			return;
//			}
		} catch (Exception e) {
			System.out.println("oauth Error");
			throw e;
		}

	}

	// TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
	private OAuthDTO.OAuthSuccess loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
		String accessToken = jwtService.createAccessToken((String) oAuth2User.getAttributes().get("login"));
		String refreshToken = jwtService.createRefreshToken();
		System.out.println("accessToken="+accessToken);
		System.out.println("refreshToken="+refreshToken);
		response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
		response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

		jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		jwtService.updateRefreshToken((String) oAuth2User.getAttributes().get("login"), refreshToken);

		return OAuthDTO.OAuthSuccess.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}


}
