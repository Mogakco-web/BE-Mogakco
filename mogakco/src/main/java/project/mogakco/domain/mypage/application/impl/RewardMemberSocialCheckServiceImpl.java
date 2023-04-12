package project.mogakco.domain.mypage.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.RewardMemberSocialCheckService;
import project.mogakco.domain.mypage.entity.Reward;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;
import project.mogakco.domain.mypage.repo.RewardMemberSocialRepository;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class RewardMemberSocialCheckServiceImpl implements RewardMemberSocialCheckService {

	private final RewardMemberSocialRepository rewardMemberSocialRepository;

	@Override
	public Optional<RewardMemberSocial> getInfoRMByRNameAndM(String name, MemberSocial memberSocial) {
		return rewardMemberSocialRepository.findByMemberSocialAndRewardName(memberSocial,name);
	}
}
