package project.mogakco.domain.mypage.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MyPageResponseDTO {

	@Getter
	@Builder
	public static class ContinueCount{
		private LocalDate lastUseDay;
		private int count;
	}
}
