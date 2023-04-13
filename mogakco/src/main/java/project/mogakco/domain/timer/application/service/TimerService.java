package project.mogakco.domain.timer.application.service;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;
import project.mogakco.domain.timer.entity.Timer;

import java.util.List;

public interface TimerService {

	ResponseEntity<?> recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday);

	ResponseEntity<?> getTodayInfo(TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO);

	ResponseEntity<?> getDiffYesterdayInfo(TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO);

	List<Timer> getTimerAllInfo();
  
	ResponseEntity<?> getDiffWeekInfo(String oauthId);

	List<Timer> getTimerInfoListByMemberSocial(MemberSocial memberSocial);
}
