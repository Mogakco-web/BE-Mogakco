package project.mogakco.global.handler.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.application.service.GithubSocialService;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.global.application.jwt.JwtService;
import project.mogakco.global.domain.entity.oauth.CustomOAuth2User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final GithubSocialService githubSocialService;
	private final CategoryService categoryService;
	private final MemberServiceImpl memberService;
//    private final UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.info("OAuth2 Login 성공!");
		log.info("callback-git url : "+request.getRequestURI());
		try {
			CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
			log.info("OAuth2User="+oAuth2User.getAttributes().get("login"));
			// User의 Role이 GyUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
			/*if(oAuth2User.getRole() == Role.GUEST) {
				String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
				response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
				response.sendRedirect("oauth2/sign-up"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
				System.out.println("accessToken");
				jwtService.sendAccessAndRefreshToken(response, accessToken, null);
//                User findUser = userRepository.findByEmail(oAuth2User.getEmail())
//                                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
//                findUser.authorizeUser();
			} else {*/
			loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
			initializeCategorySettings((String) oAuth2User.getAttributes().get("login"));
			response.sendRedirect("http://localhost:3000/callback?accessToken="+response.getHeader("Authorization")+"&refreshToken="+response.getHeader("Authorization_refresh"));
			return;
//			}
		} catch (Exception e) {
			System.out.println("oauth Error");
			throw e;
		}

	}

	// TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
	private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
		String accessToken = jwtService.createAccessToken((String) oAuth2User.getAttributes().get("login"));
		String refreshToken = jwtService.createRefreshToken();
		System.out.println("accessToken="+accessToken);
		System.out.println("refreshToken="+refreshToken);
		response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
		response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

		jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		jwtService.updateRefreshToken((String) oAuth2User.getAttributes().get("login"), refreshToken);
	}

	private MemberSocial initializeCategorySettings(String nickname){
		MemberSocial findM = memberService.getMemberInfoByNickname(nickname);
		if (categoryService.getCategoryInfoNameAndMember(findM)){
			log.info("category settings");
		}else {
			categoryService.initializeBasicCategory(findM);
		}

		return findM;
	}
}
