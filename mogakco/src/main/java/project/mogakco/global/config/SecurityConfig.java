package project.mogakco.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import project.mogakco.domain.member.repository.MemberRepository;
import project.mogakco.global.application.LoginService;
import project.mogakco.global.application.jwt.JwtService;
import project.mogakco.global.application.oauth.CustomOAuth2UserService;
import project.mogakco.global.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import project.mogakco.global.filter.jwt.JwtAuthenticationProcessingFilter;
import project.mogakco.global.handler.oauth.OAuth2LoginFailureHandler;
import project.mogakco.global.handler.oauth.OAuth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	private final LoginService loginService;
	private final JwtService jwtService;
	private final MemberRepository memberRepository;
	private final ObjectMapper objectMapper;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.formLogin().disable() // FormLogin 사용 X
				.httpBasic().disable() // httpBasic 사용 X
				.csrf().disable() // csrf 보안 사용 X
				.headers().frameOptions().disable()
				.and()

				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()

				.authorizeRequests()

				.antMatchers("/swagger-ui/**", "/v3/api-docs/**","swagger-ui/null/swagger-resources/configuration/ui").permitAll() // Swagger UI 접근 권한 추가
				.antMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()
				.antMatchers("/oauth2/authorization/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.oauth2Login()
				.successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때 Handler 설정
				.failureHandler(oAuth2LoginFailureHandler) // 소셜 로그인 실패 시 핸들러 설정
				.userInfoEndpoint().userService(customOAuth2UserService); // customUserService 설정


		http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
		http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * AuthenticationManager 설정 후 등록
	 * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
	 * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
	 * UserDetailsService는 커스텀 LoginService로 등록
	 * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
	 *
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(loginService);
		return new ProviderManager(provider);
	}

	/**
	 * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
	 */
/*
	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler(jwtService, memberRepository);
	}

	*/
/**
 * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
 *//*

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		return new LoginFailureHandler();
	}
*/

	/**
	 * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
	 * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
	 * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
	 * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
	 */
	@Bean
	public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
		CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
				= new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
		customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
		return customJsonUsernamePasswordLoginFilter;
	}

	@Bean
	public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
		JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
		return jwtAuthenticationFilter;
	}
}