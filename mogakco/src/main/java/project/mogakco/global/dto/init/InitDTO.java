package project.mogakco.global.dto.init;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class InitDTO {


	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class BasicSetting{
		private String oauthId;
		private String fcmToken;

		@Builder
		public BasicSetting(String oauthId, String fcmToken) {
			this.oauthId = oauthId;
			this.fcmToken = fcmToken;
		}
	}
}
