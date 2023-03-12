package project.mogakco.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDTO {

	@Getter
	public static class OnlyRefreshTokenDTO{
		private String refreshToken;
	}

	@Getter
	@Setter
	public static class UpdateOAuthUser{
		private String imgUrl;
		private String nickname;
		private String authToken;
	}
}
