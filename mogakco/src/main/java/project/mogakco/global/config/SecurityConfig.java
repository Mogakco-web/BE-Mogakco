package project.mogakco.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.mogakco.domain.member.application.service.CustomOAuth2UserService;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomOAuth2UserService oAuth2UserService;
//	private final OAuth2SuccessHandler successHandler;
//	private final TokenService tokenService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.oauth2Login().defaultSuccessUrl("/api/v1/oauth/loginInfo",true)
				.userInfoEndpoint()
				.userService(oAuth2UserService);

	}
}