package project.mogakco.global.dto.oauth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthDTO {

	@Getter
	@Builder
	public static class OAuthSuccess{
		private String accessToken;
		private String refreshToken;
	}
}
