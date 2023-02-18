package project.mogakco.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDTO {

	@Getter
	public static class OnlyRefreshTokenDTO{
		private String refreshToken;
	}
}
