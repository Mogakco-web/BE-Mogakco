package project.mogakco.domain.timer.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;

@RestController
@RequestMapping("/api/v1/timer")
@RequiredArgsConstructor
public class TimerAPIController {

	private final TimerService timerService;

	@PostMapping("/recode")
	public ResponseEntity<?> recodeTodayTimeInfo(@RequestBody TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		return timerService.recodeTimeToday(timerRecodeInfoToday);
	}

	@GetMapping("/todayInfo")
	public ResponseEntity<?> getTodayInfo(@RequestBody TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO){
		return timerService.getTodayInfo(todayDateInfoDTO);
	}

	@GetMapping("/compareYes")
	public ResponseEntity<?> getInfoCompareYesterDay(@RequestBody TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO){
		return timerService.getDiffYesterdayInfo(diffYesterdayDateCompareDTO);
	}
}
