package project.mogakco.domain.mypage.application.impl.reward;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.reward.RewardService;
import project.mogakco.domain.mypage.application.service.reward.RewardTimerService;

@Service
@RequiredArgsConstructor
public class RewardTimerServiceImpl implements RewardTimerService {

	private final RewardService rewardService;

	@Override
	public void initializeTimerReward(String type, MemberSocial memberSocial,String fcmToken) {
		rewardService.initializeRewardService(type,memberSocial,fcmToken);
	}
}
