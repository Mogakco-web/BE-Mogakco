package project.mogakco.domain.timer.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mogakco.domain.member.entity.member.MemberRole;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.member.entity.member.SocialType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Getter
public class TimerResponseDTO {

	@Getter
	@Builder
	public static class RecodeTime{
		private Long timer_seq;

		private Long member_Seq;

		private String recodeTime;

		private long day_of_totalTime;

		private String timerCreDay;
	}

	@Getter
	@Builder
	public static class TimeInfo{
		private String recodeTime;

		private String timerCreDay;
	}
}
