package project.mogakco.domain.mypage.application.service;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.member.entity.member.MemberSocial;

public interface RewardService {
	void initializeRewardService(String type, MemberSocial memberSocial);
}
