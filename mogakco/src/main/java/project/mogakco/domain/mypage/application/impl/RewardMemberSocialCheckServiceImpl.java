package project.mogakco.domain.mypage.application.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.reward.RewardMemberSocialCheckService;
import project.mogakco.domain.mypage.entity.QReward;
import project.mogakco.domain.mypage.entity.QRewardMemberSocial;
import project.mogakco.domain.mypage.entity.Reward;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;
import project.mogakco.domain.mypage.repo.RewardMemberSocialRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class RewardMemberSocialCheckServiceImpl implements RewardMemberSocialCheckService {

	private final RewardMemberSocialRepository rewardMemberSocialRepository;

	private final JPAQueryFactory jpaQueryFactory;

	private final MemberServiceImpl memberService;

	@Override
	public Optional<RewardMemberSocial> getInfoRMByRNameAndM(String name, MemberSocial memberSocial) {
		return rewardMemberSocialRepository.findByMemberSocialAndRewardName(memberSocial,name);
	}

	@Override
	public List<Reward> getInfoRMListByM(String oauthId) {
		MemberSocial memberSocial = memberService.getMemberInfoByOAuthId(oauthId);
		QRewardMemberSocial qRewardMemberSocial=QRewardMemberSocial.rewardMemberSocial;
		QReward qReward=QReward.reward;

		return jpaQueryFactory.select(qReward)
				.from(qRewardMemberSocial)
				.where(qRewardMemberSocial.memberSocial.eq(memberSocial))
				.fetch();
	}

}
