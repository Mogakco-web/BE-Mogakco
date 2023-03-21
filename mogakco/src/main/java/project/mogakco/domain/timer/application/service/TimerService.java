package project.mogakco.domain.timer.application.service;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;

import java.time.LocalDate;

public interface TimerService {

	ResponseEntity<?> recodeInitialize(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday);

	ResponseEntity<?> recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday);

	ResponseEntity<?> getTodayInfo(TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO);

	ResponseEntity<?> getDiffYesterdayInfo(TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO);

	ResponseEntity<?> getDiffWeekInfo(String oauthId);
}
