package project.mogakco.global.filter.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;
import project.mogakco.global.application.jwt.JwtService;
import project.mogakco.global.util.PasswordUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

	private static final String[] NO_CHECK_URL={
			"/oauth2/authorization/github",
			"/login/oauth2/code/github",
			"/WalkupScanToComp/WalkupScanToCompDestinations"
	};
	// /login"으로 들어오는 요청은 Filter 작동 X

	private final JwtService jwtService;
	private final MemberRepository memberRepository;

	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		System.out.println("URI="+request.getRequestURI());
		String requestURI = request.getRequestURI();
		String substring = requestURI.substring(0, 11);
		if(substring.equals("/swagger-ui")){
			return;
		}
		for (int i=0;i<NO_CHECK_URL.length;i++){
			if (requestURI.equals(NO_CHECK_URL[i])){
				filterChain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
				return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
			}
		}
		// 사용자 요청 헤더에서 RefreshToken 추출
		// -> RefreshToken이 없거나 유효하지 않다면(DB에 저장된 RefreshToken과 다르다면) null을 반환
		// 사용자의 요청 헤더에 RefreshToken이 있는 경우는, AccessToken이 만료되어 요청한 경우밖에 없다.
		// 따라서, 위의 경우를 제외하면 추출한 refreshToken은 모두 null
		String refreshToken = jwtService.extractRefreshToken(request)
				.filter(jwtService::isTokenValid)
				.orElse(null);


//		if (jwtService.extracUserInfo(request).isPresent()){
//			return;
//		};



		if (refreshToken != null) {
			checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
			return;
		}

		if (refreshToken == null) {
			checkAccessTokenAndAuthentication(request, response, filterChain);

		}
	}


	/**
	 *  [리프레시 토큰으로 유저 정보 찾기 & 액세스 토큰/리프레시 토큰 재발급 메소드]
	 *  파라미터로 들어온 헤더에서 추출한 리프레시 토큰으로 DB에서 유저를 찾고, 해당 유저가 있다면
	 *  JwtService.createAccessToken()으로 AccessToken 생성,
	 *  reIssueRefreshToken()로 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드 호출
	 *  그 후 JwtService.sendAccessTokenAndRefreshToken()으로 응답 헤더에 보내기
	 */
	public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
		memberRepository.findByRefreshToken(refreshToken)
				.ifPresent(member -> {
					String reIssuedRefreshToken = reIssueRefreshToken(member);
					jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(member.getNickname()),
							reIssuedRefreshToken);
				});
	}

	/**
	 * [리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드]
	 * jwtService.createRefreshToken()으로 리프레시 토큰 재발급 후
	 * DB에 재발급한 리프레시 토큰 업데이트 후 Flush
	 */
	private String reIssueRefreshToken(MemberSocial member) {
		String reIssuedRefreshToken = jwtService.createRefreshToken();
		member.updateRefreshToken(reIssuedRefreshToken);
		memberRepository.saveAndFlush(member);
		return reIssuedRefreshToken;
	}

	/**
	 * [액세스 토큰 체크 & 인증 처리 메소드]
	 * request에서 extractAccessToken()으로 액세스 토큰 추출 후, isTokenValid()로 유효한 토큰인지 검증
	 * 유효한 토큰이면, 액세스 토큰에서 extractEmail로 Email을 추출한 후 findByEmail()로 해당 이메일을 사용하는 유저 객체 반환
	 * 그 유저 객체를 saveAuthentication()으로 인증 처리하여
	 * 인증 허가 처리된 객체를 SecurityContextHolder에 담기
	 * 그 후 다음 인증 필터로 진행
	 */
	public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
												  FilterChain filterChain) throws ServletException, IOException {
		log.info("checkAccessTokenAndAuthentication() 호출");
		jwtService.extractAccessToken(request)
				.filter(jwtService::isTokenValid)
				.filter(token->jwtService.isTokenExpired(response,token))
				.ifPresent(accessToken -> jwtService.extractNickname(accessToken)
						.ifPresent(nickname -> memberRepository.findByNickname(nickname)
								.ifPresent(this::saveAuthentication)));
		if (response.getStatus()==401){
			response.getWriter().write("UnAuthorization");
			return;
		}
		System.out.println(response.getStatus());
		filterChain.doFilter(request, response);
	}


	public void saveAuthentication(MemberSocial member) {
		String password = member.getPassword();
		if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
			password = PasswordUtil.generateRandomPassword();
		}

		UserDetails userDetailsUser = User.builder()
				.username(member.getNickname())
				.password(password)
				.roles(member.getRole().name())
				.build();

		Authentication authentication =
				new UsernamePasswordAuthenticationToken(userDetailsUser, null,
						authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
