package project.mogakco.domain.timer.application.service;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;
import project.mogakco.domain.timer.entity.Timer;

import java.time.LocalDate;
import java.util.List;

public interface TimerService {
	ResponseEntity<?> recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday);

	ResponseEntity<?> getTodayInfo(TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO);

	ResponseEntity<?> getDiffYesterdayInfo(TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO);

	void getDiffWeekInfo(String oauthId);

	List<Timer> getTimerAllInfo();
}
