package project.mogakco.domain.mypage.application.service.reward;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.member.entity.member.MemberSocial;

public abstract class RewardService {
	public abstract void initializeRewardService(String type, MemberSocial memberSocial);

	public abstract ResponseEntity<?> getListInfoRewardList();
}
