package project.mogakco.domain.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MyPageDTO {

	@Getter
	public static class totalTimerUse{
		private String oauthId;
	}

	@Getter
	public static class continueTimer{
		private String oauthId;
		private LocalDate nowDate;
	}
}
