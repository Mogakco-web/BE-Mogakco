package project.mogakco.domain.mypage.application.service.reward;

import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.dto.response.RewardDTO;
import project.mogakco.domain.mypage.entity.Reward;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;

import java.util.List;
import java.util.Optional;

public interface RewardMemberSocialCheckService {
	Optional<RewardMemberSocial> getInfoRMByRNameAndM(String name, MemberSocial memberSocial);
	List<RewardDTO.listReward> getInfoRMListByOId(String oauthId);
	List<RewardMemberSocial> getInfoRMListByM(MemberSocial memberSocial);
}
