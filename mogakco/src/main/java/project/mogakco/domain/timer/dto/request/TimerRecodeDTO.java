package project.mogakco.domain.timer.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TimerRecodeDTO {

	@Getter
	@Setter
	public static class timerRecodeInfoToday{
		private String hours;
		private String minute;
		private String second;
		private LocalDate timerCreDay;
		private String oauthId;

	}

	@Getter
	public static class todayDateInfoDTO{
		private String oauthId;
		private LocalDate timerCreDay;
	}

	@Getter
	public static class diffYesterdayDateCompareDTO{
		private String oauthId;
		private LocalDate todayDateInfo;
		private LocalDate yesterdayDateInfo;
	}

}
