package project.mogakco.domain.mypage.application.impl.reward;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.reward.RewardService;
import project.mogakco.domain.mypage.entity.DummyReward;
import project.mogakco.domain.mypage.entity.Reward;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;
import project.mogakco.domain.mypage.repo.RewardMemberSocialRepository;
import project.mogakco.domain.mypage.repo.RewardRepository;
import project.mogakco.domain.timer.application.service.TimerCheckService;
import project.mogakco.global.application.fcm.service.FCMService;
import project.mogakco.global.config.RewardConfig;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RewardServiceImpl extends RewardService {

	private final RewardMemberSocialRepository rewardMemberSocialRepository;

	private final RewardRepository rewardRepository;

	private final TimerCheckService timerCheckService;

	private static List<DummyReward> dummyRewardList;

	private final FCMService fcmService;

	private final MemberServiceImpl memberService;


	@PostConstruct
	public void initializeReward(){
		dummyRewardList=initializeRewardList();
	}

	@Override
	public void initializeRewardService(String type,MemberSocial memberSocial,String fcmToken) {
		switch (type){
			case "oauth":
				oauthInitialize(memberSocial,fcmToken);
				break;
			case "timer":
				timerInitialize(memberSocial,fcmToken);
				break;
		}
	}



	@Transactional
	public void timerInitialize(MemberSocial memberSocial,String fcmToken) {
		switch (timerCheckService.getTimerInfoListByMemberSocial(memberSocial)
				.size()
		){
			case 1:
				saveRewardMemberSocial(saveOnlyReward(RewardConfig.TimerOne.title,
						RewardConfig.TimerOne.description),memberSocial,fcmToken);
				break;
			case 50:
				saveRewardMemberSocial(saveOnlyReward(RewardConfig.TimerFifty.title,
						RewardConfig.TimerFifty.description),memberSocial,fcmToken);
				break;
			case 100:
				saveRewardMemberSocial(saveOnlyReward(RewardConfig.TimerHundred.title,
						RewardConfig.TimerHundred.description),memberSocial,fcmToken);
				break;
			case 500:
				saveRewardMemberSocial(saveOnlyReward(RewardConfig.TimerFHundred.title,
						RewardConfig.TimerFHundred.description),memberSocial,fcmToken);
				break;
		}
	}

	@Transactional
	public void oauthInitialize(MemberSocial memberSocial,String fcmToken){
		saveRewardMemberSocial(saveOnlyReward(RewardConfig.Newbie.title,RewardConfig.Newbie.description), memberSocial,fcmToken);
	}

	public Reward saveOnlyReward(String reward_name,String reward_description){
		return rewardRepository.save(
				Reward.builder()
						.name(reward_name)
						.description(reward_description)
						.build()
		);

	}

	private void messagingToClientReward(String title,String contents,String fcmToken){
		System.out.println("t="+title);
		System.out.println("c="+contents);
		System.out.println("f="+fcmToken);
		fcmService.sendNotificationReward(title,contents,fcmToken);
	}

	@Transactional
	public void saveRewardMemberSocial(Reward reward, MemberSocial memberSocial,String fcmToken){
		System.out.println("FCMTOKEN="+fcmToken);
		if(fcmToken!=null){
			messagingToClientReward(reward.getName(),reward.getDescription(),fcmToken);
		}
		rewardMemberSocialRepository.save(
				RewardMemberSocial.builder()
						.reward(reward)
						.memberSocial(memberSocial)
						.build()
		);
	}

	@Override
	public ResponseEntity<?> getListInfoRewardList() {
		return new ResponseEntity<>(dummyRewardList, HttpStatus.OK);
	}

	private List<DummyReward> initializeRewardList(){
		LinkedList<String> name_list= RewardConfig.getTitles();

		LinkedList<String> des_list= RewardConfig.getDescriptions();
		List<DummyReward> dummyList=new ArrayList<>();
		for (int i=0;i<name_list.size();i++){
			dummyList.add(DummyReward.builder()
							.name(name_list.get(i))
							.description(des_list.get(i))
					.build());
		}
		return dummyList;
	}
}
