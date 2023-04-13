package project.mogakco.domain.mypage.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.reward.RewardService;
import project.mogakco.domain.mypage.entity.Reward;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;
import project.mogakco.domain.mypage.repo.RewardMemberSocialRepository;
import project.mogakco.domain.mypage.repo.RewardRepository;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.repo.TimerRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

	private final RewardMemberSocialRepository rewardMemberSocialRepository;

	private final RewardRepository rewardRepository;

	private final TimerRepository timerRepository;

	@Override
	public void initializeRewardService(String type,MemberSocial memberSocial) {
		switch (type){
			case "oauth":
				oauthInitialize(memberSocial);
				break;
			case "timer":
				timerInitialize(memberSocial);
		}
	}

	@Transactional
	public void timerInitialize(MemberSocial memberSocial) {
		switch (timerRepository.findByMemberSocial(memberSocial)
				.get()
				.size()
		){
			case 1:
				saveRewardMemberSocial(saveOnlyReward("부서진 초시계(을)를 얻었다"),memberSocial);
				break;
			case 50:
				saveRewardMemberSocial(saveOnlyReward("초시계(750G) 획득!"),memberSocial);
				break;
			case 100:
				saveRewardMemberSocial(saveOnlyReward("존야의 모래시계"),memberSocial);
				break;
			case 500:
				saveRewardMemberSocial(saveOnlyReward("시간의 지배자"),memberSocial);
				break;
		}
	}

	@Transactional
	public void oauthInitialize(MemberSocial memberSocial){
		saveRewardMemberSocial(saveOnlyReward("뉴비"), memberSocial);
	}

	public Reward saveOnlyReward(String reward_name){
		return rewardRepository.save(
				Reward.builder()
						.name(reward_name)
						.build()
		);

	}

	@Transactional
	public void saveRewardMemberSocial(Reward reward, MemberSocial memberSocial){
		rewardMemberSocialRepository.save(
				RewardMemberSocial.builder()
						.reward(reward)
						.memberSocial(memberSocial)
						.build()
		);
	}

}
