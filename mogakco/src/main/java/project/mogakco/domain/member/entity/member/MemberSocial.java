package project.mogakco.domain.member.entity.member;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MemberSocial {

	@Id
	@GeneratedValue
	private Long member_seq;

	private String email;

	private String member_imgUrl;

	private String member_social_id;

	@Enumerated
	private MemberRole role;

	@Enumerated
	private AuthProvider authProvider;


	@Builder
	public MemberSocial(String email, String member_imgUrl, String member_social_id, MemberRole role, AuthProvider authProvider) {
		this.email = email;
		this.member_imgUrl = member_imgUrl;
		this.member_social_id = member_social_id;
		this.role = role;
		this.authProvider = authProvider;
	}

	public MemberSocial() {

	}

	public MemberSocial updateNewUserInfo(String member_social_email,String member_social_id,String member_imgUrl){
		this.member_imgUrl=member_imgUrl;
		this.email =member_social_email;
		this.member_social_id=member_social_id;
		return this;
	}
}
