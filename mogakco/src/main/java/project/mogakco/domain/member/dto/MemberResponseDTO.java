package project.mogakco.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mogakco.domain.member.entity.member.MemberRole;
import project.mogakco.domain.member.entity.member.SocialType;


@Builder
@Getter
public class MemberResponseDTO {
	private Long member_seq;

	private String email;

	private String nickname;

	private String member_imgUrl;

	private String oauthId;

	private MemberRole role;

	private SocialType socialType;

	private String authToken;

	private String refreshToken;

}
