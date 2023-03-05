package project.mogakco.domain.timer.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timer")
public class TimerAPIController {

	private final TimerService timerService;

	@PostMapping("/recodeTime")
	public ResponseEntity<?> recodeTimeToLearn(@RequestBody TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		return timerService.recodeTimeToday(timerRecodeInfoToday);
	}
}
