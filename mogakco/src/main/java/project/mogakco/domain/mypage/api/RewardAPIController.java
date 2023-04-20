package project.mogakco.domain.mypage.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.mypage.application.service.reward.RewardMemberSocialCheckService;
import project.mogakco.domain.mypage.application.service.reward.RewardService;
import project.mogakco.domain.mypage.dto.request.RewardRequestDTO;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/reward")
public class RewardAPIController {

	private final RewardMemberSocialCheckService rewardMemberSocialCheckService;

	private final RewardService rewardService;

	@PostMapping
	public ResponseEntity<?> getRewardInfo(@RequestBody RewardRequestDTO.OnlyUseOauthId onlyUseOauthId) {
		return new ResponseEntity<>(rewardMemberSocialCheckService.
				getInfoRMListByOId(onlyUseOauthId.getOauthId())
										, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getRewardListInfo(){
		return new ResponseEntity<>(rewardService.getListInfoRewardList(),HttpStatus.OK);
	}
}
