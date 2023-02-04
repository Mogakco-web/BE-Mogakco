package project.mogakco.domain.member.entity.oauth;

import lombok.Getter;
import lombok.Setter;
import project.mogakco.domain.member.entity.member.AuthProvider;
import project.mogakco.domain.member.entity.member.MemberRole;
import project.mogakco.domain.member.entity.member.MemberSocial;

@Getter
@Setter
public class OAuth2UserInfo {
	private String id;
	private String name;
	private String email;
	private String provider;
	private String imageUrl;
	public MemberSocial toMember() {
		return MemberSocial.builder()
				.member_social_id(name)
				.role(MemberRole.MEMBER)
				.member_social_email(email)
				.authProvider(AuthProvider.changeStringAuthProvider(provider))
				.build();
	}
}
