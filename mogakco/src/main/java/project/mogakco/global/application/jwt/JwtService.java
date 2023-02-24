package project.mogakco.global.application.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Log4j2
@Transactional
public class JwtService {

	private String secretKey="Snd0QVBJU2VydmljZUFuZFdlYlNvY2tldFByb2plY3Q=";

	private Long accessTokenExpirationPeriod=86400L;

	private Long refreshTokenExpirationPeriod=604800L;

	private String accessHeader="Authorization";

	private String refreshHeader="Authorization_refresh";


	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String NICKNAME_CLAIM = "email";
	private static final String BEARER = "Bearer ";

	private final MemberRepository memberRepository;


	public String createAccessToken(String name) {
		Date now = new Date();
		return JWT.create()
				.withSubject(ACCESS_TOKEN_SUBJECT)
				.withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
				.withClaim(NICKNAME_CLAIM, name)
				.sign(Algorithm.HMAC512(secretKey));
	}

	public String createRefreshToken() {
		Date now = new Date();
		return JWT.create()
				.withSubject(REFRESH_TOKEN_SUBJECT)
				.withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
				.sign(Algorithm.HMAC512(secretKey));
	}

	public void sendAccessToken(HttpServletResponse response, String accessToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		response.setHeader(accessHeader, accessToken);
		log.info("재발급된 Access Token : {}", accessToken);
	}

	public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		setAccessTokenHeader(response, accessToken);
		setRefreshTokenHeader(response, refreshToken);
		log.info("Access Token, Refresh Token 헤더 설정 완료");
	}

	public Optional<String> extracUserInfo(HttpServletRequest request) {
		log.info("userInfoReqeuest="+request.getHeader("userInfo"));
		return Optional.ofNullable(request.getHeader("userInfo"));
	}

	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(refreshHeader))
				.filter(refreshToken -> refreshToken.startsWith(BEARER))
				.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

		public Optional<String> extractAccessToken(HttpServletRequest request) {
			System.out.println("HeaderAccess="+request.getHeader(accessHeader));
			Optional<String> s = Optional.ofNullable(request.getHeader(accessHeader))
					.filter(refreshToken -> refreshToken.startsWith(BEARER))
					.map(refreshToken -> refreshToken.replace(BEARER, ""));
			System.out.println("s="+s);
			return s;
	}

	public Optional<String> extractNickname(String accessToken) {
		try {
			return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
					.build()
					.verify(accessToken)
					.getClaim(NICKNAME_CLAIM)
					.asString());
		} catch (Exception e) {
			log.error("액세스 토큰이 유효하지 않습니다.");
			return Optional.empty();
		}
	}

	public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(accessHeader, accessToken);
	}

	public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
		response.setHeader(refreshHeader, refreshToken);
	}

	public void updateRefreshToken(String nickname, String refreshToken) {
		memberRepository.findByNickname(nickname)
				.ifPresentOrElse(
						member -> member.updateRefreshToken(refreshToken),
						() -> new Exception("일치하는 회원이 없습니다.")
				);
	}

	public boolean isTokenValid(String token) {
		System.out.println("token="+token);
		try {
			DecodedJWT verify = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
			System.out.println("verify="+verify);
			return true;
		} catch (Exception e) {
			log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
			return false;
		}
	}

}
