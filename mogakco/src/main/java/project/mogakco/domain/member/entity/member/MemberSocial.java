package project.mogakco.domain.member.entity.member;

import lombok.*;
import project.mogakco.global.domain.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class MemberSocial extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long member_seq;

	private String email;

	private String nickname;

	private String member_imgUrl;

	private String oauthId;

	@Enumerated(EnumType.STRING)
	private MemberRole role;

	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	private String refreshToken;

	private String password;
	public void updateRefreshToken(String updateRefreshToken){
		this.refreshToken=updateRefreshToken;
	}
}
