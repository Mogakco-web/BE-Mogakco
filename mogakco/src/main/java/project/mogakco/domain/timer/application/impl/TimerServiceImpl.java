package project.mogakco.domain.timer.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;
import project.mogakco.domain.timer.entity.Timer;
import project.mogakco.domain.timer.repo.TimerRepository;

@RequiredArgsConstructor
@Log4j2
public class TimerServiceImpl implements TimerService {

	private final TimerRepository timerRepository;

	@Override
	public ResponseEntity<?> recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday) {
		timerRecodeInfoToday.setDay_of_totalTime(sumOfDayTime(timerRecodeInfoToday));
		Timer t = timerRepository.save(timerRecodeInfoToday.toEntity());
		return new ResponseEntity<>(t, HttpStatus.OK);
	}


	private int sumOfDayTime(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		int hours_sec = Integer.parseInt(timerRecodeInfoToday.getHours()) * 60 * 60;
		int minute_sec = Integer.parseInt(timerRecodeInfoToday.getMinute()) * 60;
		int sec_sec = Integer.parseInt(timerRecodeInfoToday.getHours());
		return hours_sec+minute_sec+sec_sec;
	}
}
