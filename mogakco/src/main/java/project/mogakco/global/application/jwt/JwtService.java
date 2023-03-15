package project.mogakco.global.application.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.repository.MemberRepository;
import project.mogakco.global.exception.TokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Getter
@Log4j2
@Transactional
public class JwtService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.access.expiration}")
	private int accessTokenExpirationPeriod;

	@Value("${jwt.refresh.expiration}")
	private int refreshTokenExpirationPeriod;

	@Value("${jwt.access.header}")
	private String accessHeader;

	@Value("${jwt.refresh.header}")
	private String refreshHeader;


	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String NICKNAME_CLAIM = "email";
	private static final String BEARER = "Bearer ";

	private final MemberRepository memberRepository;



	public String createAccessToken(String name) {
		return JWT.create()
				.withSubject(ACCESS_TOKEN_SUBJECT)
				.withExpiresAt(changeDateStandardToSeoul("access"))
				.withClaim(NICKNAME_CLAIM, name)
				.sign(Algorithm.HMAC512(secretKey));
	}

	public String createRefreshToken() {
		return JWT.create()
				.withSubject(REFRESH_TOKEN_SUBJECT)
				.withExpiresAt(changeDateStandardToSeoul("refresh"))
				.sign(Algorithm.HMAC512(secretKey));
	}

	public void sendAccessToken(HttpServletResponse response, String accessToken) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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

	public boolean isTokenValid(HttpServletResponse response,String token) {
		try {
			DecodedJWT verify = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
			return true;
		} catch (Exception e) {
			log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
			if (e.getClass().equals(TokenExpiredException.class)){
				log.info("EXPIRED EXCEPTION");
				sendAccessToken(response,token);
			}
			return false;
		}
	}

	private Date changeDateStandardToSeoul(String token_type){
		TimeZone seoulTimeZone = TimeZone.getTimeZone("Asia/Seoul");

		if (token_type.equals("access")){
			Date access = new Date(System.currentTimeMillis() + accessTokenExpirationPeriod * 1000);
			access.setTime(access.getTime()+seoulTimeZone.getOffset(access.getTime()));
			System.out.println("log1="+access.getTime());
			return access;
		}else {
			Date refresh = new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod * 1000);
			refresh.setTime(refresh.getTime()+seoulTimeZone.getOffset(refresh.getTime()));
			System.out.println("refresh="+refresh.getTime());
			return refresh;
		}
	}

}
