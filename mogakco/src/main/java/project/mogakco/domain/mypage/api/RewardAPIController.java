package project.mogakco.domain.mypage.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.mypage.application.service.RewardMemberSocialCheckService;
import project.mogakco.domain.mypage.dto.request.RewardRequestDTO;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/reward")
public class RewardAPIController {

	private final RewardMemberSocialCheckService rewardMemberSocialCheckService;

//	@PostMapping
//	public ResponseEntity<?> getRewardInfo(@RequestBody RewardRequestDTO.OnlyUseOauthId onlyUseOauthId){

}
