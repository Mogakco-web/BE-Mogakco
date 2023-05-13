package project.mogakco.global.application.init.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.reward.RewardMemberSocialCheckService;
import project.mogakco.domain.mypage.application.service.reward.RewardService;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.global.application.init.service.InitService;
import project.mogakco.global.dto.init.InitDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class InitServiceImpl implements InitService {

	private final MemberServiceImpl memberService;
	private final CategoryService categoryService;
	private final RewardMemberSocialCheckService rewardMemberSocialCheckService;
	private final RewardService rewardService;

	@Override
	@Transactional
	public void basicSetting(InitDTO.BasicSetting basicSetting){
		MemberSocial findM = memberService.getMemberInfoByOAuthId(basicSetting.getOauthId());
		System.out.println("Basic oauth="+basicSetting.getOauthId());
		System.out.println("Basic FINDM="+findM);

		findM.updateInfoToFCMToken(Optional.ofNullable(findM.getFcmToken())
				.orElse(basicSetting.getFcmToken()));
		initializeCategorySettings(findM);
		isNewbie(findM);
	}

	private void initializeCategorySettings(MemberSocial memberSocial){
		if (categoryService.getCategoryInfoNameAndMember(memberSocial)){
			log.info("category settings");
		}else {
			categoryService.initializeBasicCategory(memberSocial);
		}
	}


	private void isNewbie(MemberSocial findM){
		if(rewardMemberSocialCheckService.getInfoRMListByM(findM).isEmpty()){
			rewardToNewbie(findM);
		}
 	}

	@Transactional
	public void rewardToNewbie(MemberSocial memberSocial) {
		Optional<RewardMemberSocial> findRM = rewardMemberSocialCheckService.getInfoRMByRNameAndM("뉴비", memberSocial);
		if (findRM.isEmpty()){
			System.out.println("뉴비!");
			rewardService.initializeRewardService("oauth",memberSocial,memberSocial.getFcmToken());
		}
	}
}
