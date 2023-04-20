package project.mogakco.domain.mypage.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.mypage.application.service.MyPageService;
import project.mogakco.domain.mypage.dto.MyPageDTO;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/api/v1/mypage",produces = "application/json; charset=utf8")
public class MyPageAPIController {

	private final MyPageService myPageService;

	@PostMapping("/totalTimer")
	public ResponseEntity<?> getMemberTimerTotalInfo(@RequestBody MyPageDTO.onlyUseOauthId totalTimerUse){
		return myPageService.getTotalTimerUse(totalTimerUse);
	}

	@PostMapping("/contiDay")
	public ResponseEntity<?> getMemberTimerInfoContinue(@RequestBody MyPageDTO.continueTimer continueTimer){
		return myPageService.continueTimerDay(continueTimer);
	}

	@PostMapping("/attachReward")
	public ResponseEntity<?> attachRewardAtMyPage(@RequestBody MyPageDTO.attachReward attachReward){
		return myPageService.attachReward(attachReward);
	}

	@GetMapping("/checkReward")
	public ResponseEntity<?> getInfoByRewardSeq(@PathParam("rewardSeq")Long rewardSeq){
		return myPageService.getInfoByRewardSeq(rewardSeq);
	}
}
