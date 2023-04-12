package project.mogakco.domain.mypage.application.service;

import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;

import java.util.Optional;

public interface RewardMemberSocialCheckService {
	Optional<RewardMemberSocial> getInfoRMByRNameAndM(String name, MemberSocial memberSocial);

}
