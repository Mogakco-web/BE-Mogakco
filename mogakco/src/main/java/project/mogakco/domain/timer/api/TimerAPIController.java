package project.mogakco.domain.timer.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;
import project.mogakco.global.application.jwt.JwtService;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/api/v1/timer",produces = "application/json; charset=utf8")
@RequiredArgsConstructor
@Log4j2
public class TimerAPIController {

	private final TimerService timerService;

	@PostMapping("/initialize")
	public ResponseEntity<?> initializeTodayTimeInfo(@RequestBody TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		return timerService.recodeInitialize(timerRecodeInfoToday);
	}

	@PostMapping("/recode")
	public ResponseEntity<?> recodeTodayTimeInfo(@RequestBody TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		return timerService.recodeTimeToday(timerRecodeInfoToday);
	}

	@PostMapping("/todayInfo")
	public ResponseEntity<?> getTodayInfo(@RequestBody TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO){
		log.info("타이머 시간조회");
		return timerService.getTodayInfo(todayDateInfoDTO);
	}

	@PostMapping("/compareYes")
	public ResponseEntity<?> getInfoCompareYesterDay(@RequestBody TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO){
		return timerService.getDiffYesterdayInfo(diffYesterdayDateCompareDTO);
	}

	@PostMapping("/compareWeek")
	public ResponseEntity<?> test(@RequestBody TimerRecodeDTO.onlyOauthIdDTO onlyOauthIdDTO) {
		return timerService.getDiffWeekInfo(onlyOauthIdDTO.getOauthId());
	}
}
