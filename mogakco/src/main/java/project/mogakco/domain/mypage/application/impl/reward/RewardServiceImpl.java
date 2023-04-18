package project.mogakco.domain.mypage.application.impl.reward;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.reward.RewardService;
import project.mogakco.domain.mypage.entity.DummyReward;
import project.mogakco.domain.mypage.entity.Reward;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;
import project.mogakco.domain.mypage.repo.RewardMemberSocialRepository;
import project.mogakco.domain.mypage.repo.RewardRepository;
import project.mogakco.domain.timer.application.service.TimerCheckService;

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

	@PostConstruct
	public void initializeReward(){
		initializeRewardList();
	}

	@Override
	public void initializeRewardService(String type,MemberSocial memberSocial) {
		switch (type){
			case "oauth":
				oauthInitialize(memberSocial);
				break;
			case "timer":
				timerInitialize(memberSocial);
		}
	}



	@Transactional
	public void timerInitialize(MemberSocial memberSocial) {
		switch (timerCheckService.getTimerInfoListByMemberSocial(memberSocial)
				.size()
		){
			case 1:
				saveRewardMemberSocial(saveOnlyReward("부서진 초시계(을)를 얻었다"),memberSocial);
				break;
			case 50:
				saveRewardMemberSocial(saveOnlyReward("초시계(750G) 획득!"),memberSocial);
				break;
			case 100:
				saveRewardMemberSocial(saveOnlyReward("존야의 모래시계"),memberSocial);
				break;
			case 500:
				saveRewardMemberSocial(saveOnlyReward("시간의 지배자"),memberSocial);
				break;
		}
	}

	@Transactional
	public void oauthInitialize(MemberSocial memberSocial){
		saveRewardMemberSocial(saveOnlyReward("뉴비"), memberSocial);
	}

	public Reward saveOnlyReward(String reward_name){
		return rewardRepository.save(
				Reward.builder()
						.name(reward_name)
						.build()
		);

	}

	@Transactional
	public void saveRewardMemberSocial(Reward reward, MemberSocial memberSocial){
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

	private void initializeRewardList(){
		LinkedList<String> name_list= (LinkedList<String>) Arrays.asList("뉴비","부서진 초시계(을)를 얻었다",
				"초시계(750G) 획득!","존야의 모래시계","시간의 지배자");

		LinkedList<String> des_list=(LinkedList<String>) Arrays.asList("모각코에 당도한 것을 환영하오 낯선이여",
				"장비를 정지합니다","룬 : 완벽한 초시계","김동준의 가호가 함께합니다","따이무 쓰또쁘");

		for (int i=0;i<name_list.size();i++){
			dummyRewardList.add(DummyReward.builder()
							.name(name_list.get(i))
							.description(des_list.get(i))
					.build());
		}
	}
}
