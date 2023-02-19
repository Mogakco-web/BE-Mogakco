package project.mogakco.domain.timer.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.mogakco.domain.member.application.impl.GithubSocialServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.timer.entity.Timer;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TimerRecodeDTO {
	private final GithubSocialServiceImpl githubSocialService;
	@Getter
	@Setter
	public class timerRecodeInfoToday{
		private String hours;
		private String minute;
		private String second;
		private LocalDate localDate;
		private String user_oauthId;
		private long day_of_totalTime;

		public Timer toEntity(){
			return Timer.builder()
					.recodeTime(hours+":"+minute+":"+second)
					.day_of_totalTime(day_of_totalTime)
					.memberSocial(getUserInfo(user_oauthId))
					.build();
		}
	}

	@Getter
	public static class todayDateInfoDTO{
		private String oauthId;
		private LocalDate localDate;
	}

	@Getter
	public static class diffYesterdayDateCompareDTO{
		private String oauthId;
		private LocalDate todayDateInfo;
		private LocalDate yesterdayDateInfo;
	}

	private MemberSocial getUserInfo(String oauthId){
		return githubSocialService.findByOAuthId(oauthId);
	}
}
