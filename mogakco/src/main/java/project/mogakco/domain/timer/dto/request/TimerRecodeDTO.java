package project.mogakco.domain.timer.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.entity.Timer;

@Getter
@RequiredArgsConstructor
public class TimerRecodeDTO {

	@Getter
	@Setter
	public static class timerRecodeInfoToday{
		private String hours;
		private String minute;
		private String second;
		private String user_oauthId;
		private long day_of_totalTime;

		public Timer toEntity(){
			return Timer.builder()
					.recodeTime(hours+":"+minute+":"+second)
					.day_of_totalTime(day_of_totalTime)
					.build();
		}
	}
}
