package project.mogakco.domain.mypage.application.service.reward;

import project.mogakco.domain.member.entity.member.MemberSocial;

public abstract class RewardService {
	public abstract void initializeRewardService(String type, MemberSocial memberSocial);
}
