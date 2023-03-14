package project.mogakco.domain.timer.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;

@RestController
@RequestMapping(value = "/api/v1/timer",produces = "application/json; charset=utf8")
@RequiredArgsConstructor
@Log4j2
public class TimerAPIController {

	private final TimerService timerService;

	@PostMapping("/recode")
	public ResponseEntity<?> recodeTodayTimeInfo(@RequestBody TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		return timerService.recodeTimeToday(timerRecodeInfoToday);
	}

	@GetMapping("/todayInfo")
	public ResponseEntity<?> getTodayInfo(@RequestBody TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO){
		log.info("타이머 시간조회");
		return timerService.getTodayInfo(todayDateInfoDTO);
	}

	@GetMapping("/compareYes")
	public ResponseEntity<?> getInfoCompareYesterDay(@RequestBody TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO){
		return timerService.getDiffYesterdayInfo(diffYesterdayDateCompareDTO);
	}
}
