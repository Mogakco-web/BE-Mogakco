package project.mogakco.domain.mypage.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.RewardService;
import project.mogakco.domain.mypage.entity.Reward;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;
import project.mogakco.domain.mypage.repo.RewardMemberSocialRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

	private final RewardMemberSocialRepository rewardMemberSocialRepository;

	@Override
	public void initializeRewardService(String type,MemberSocial memberSocial) {
		switch (type){
			case "oauth":
				oauthInitialize(memberSocial);
		}
	}

	private void oauthInitialize(MemberSocial memberSocial){
		saveRewardMemberSocial(
				Reward.builder()
						.name("뉴비")
						.build(), memberSocial
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
