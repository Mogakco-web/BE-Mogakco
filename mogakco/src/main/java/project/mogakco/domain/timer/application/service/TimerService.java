package project.mogakco.domain.timer.application.service;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;

import java.time.LocalDate;

public interface TimerService {
	ResponseEntity<?> recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday);

	ResponseEntity<?> getTodayInfo(String oauthId, LocalDate localDate);

}