package project.mogakco.global.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.mogakco.domain.member.entity.MemberSocial;
import project.mogakco.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		MemberSocial memberSocial = memberRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));

		return org.springframework.security.core.userdetails.User.builder()
				.username(memberSocial.getMember_social_email())
				.password(memberSocial.getPassword())
				.roles(memberSocial.getRole().name())
				.build();
	}
}
