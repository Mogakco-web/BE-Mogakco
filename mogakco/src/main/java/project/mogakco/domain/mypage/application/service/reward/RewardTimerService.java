package project.mogakco.domain.mypage.application.service.reward;


import project.mogakco.domain.member.entity.member.MemberSocial;

public interface RewardTimerService {
	void initializeTimerReward(String type, MemberSocial memberSocial);
}
