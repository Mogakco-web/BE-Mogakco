package project.mogakco.domain.member.entity.member;

import lombok.Builder;

import javax.persistence.*;

@Entity
public class MemberSocial {

	@Id
	@GeneratedValue
	private Long member_seq;

	private String email;

	private String imgUrl;

	private String nickname;

	@Enumerated(EnumType.STRING)
	private MemberRole role;

	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider;

	private String gitRepoUrl;

	@Builder
	public MemberSocial(String email, String imgUrl,String gitRepoUrl, String nickname, MemberRole role, AuthProvider authProvider) {
		this.email = email;
		this.imgUrl = imgUrl;
		this.nickname = nickname;
		this.gitRepoUrl=gitRepoUrl;
		this.role = role;
		this.authProvider = authProvider;
	}

	public MemberSocial() {

	}

	public MemberSocial updateNewUserInfo(String member_social_email,String member_social_id,String member_imgUrl){
		this.imgUrl =member_imgUrl;
		this.email =member_social_email;
		this.nickname =member_social_id;
		return this;
	}
}
