package project.mogakco.domain.timer.dto.request;

import lombok.Getter;
import project.mogakco.domain.timer.entity.Timer;

@Getter
public class TimerRecodeDTO {

	public static class timerRecodeInfoToday{
		private String hours;
		private String minute;
		private String second;
		private String user_oauthId;

		public Timer toEntity(){
			return Timer.builder()
					.recodeTime(hours+":"+minute+":"+second)
					.build();
		}
	}
}
