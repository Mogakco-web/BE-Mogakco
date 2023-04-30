package project.mogakco.global.dto.init;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InitDTO {


	@Getter
	@Builder
	public static class BasicSetting{
		private Long memberSeq;
		private String fcmToken;
	}
}
