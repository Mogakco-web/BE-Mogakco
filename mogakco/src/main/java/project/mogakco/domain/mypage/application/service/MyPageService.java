package project.mogakco.domain.mypage.application.service;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.mypage.dto.MyPageDTO;

public interface MyPageService {
	ResponseEntity<?> getTotalTimerUse(MyPageDTO.onlyUseOauthId totalTimerUse);

	ResponseEntity<?> continueTimerDay(MyPageDTO.continueTimer continueTimer);

	ResponseEntity<?> attachReward(MyPageDTO.attachReward attachReward);

	ResponseEntity<?> getInfoByRewardSeq(Long rewardSeq);
}
