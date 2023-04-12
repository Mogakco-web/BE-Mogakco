package project.mogakco.domain.timer.application.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.RewardMemberSocialCheckService;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;
import project.mogakco.domain.timer.dto.response.TimerResponseDTO;
import project.mogakco.domain.timer.entity.QTimer;
import project.mogakco.domain.timer.entity.Timer;
import project.mogakco.domain.timer.repo.TimerRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
@Transactional(readOnly = true)
public class TimerServiceImpl implements TimerService {

	private final TimerRepository timerRepository;

	private final MemberServiceImpl memberService;

	private final JPAQueryFactory jpaQueryFactory;

	private final RewardMemberSocialCheckService rewardMemberSocialCheckService;

	@Override
	@Transactional
	public ResponseEntity<?> recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(timerRecodeInfoToday.getOauthId());
		Optional<Timer> findT = timerRepository.findByTimerCreDayAndMemberSocial(timerRecodeInfoToday.getTimerCreDay(), findM);
		if (findT.isEmpty()) {
			Timer t = timerRepository.save(
					Timer.builder()
							.recodeTime(changeTimeFormatToString(timerRecodeInfoToday))
							.memberSocial(findM)
							.day_of_totalTime(sumOfDayTime(timerRecodeInfoToday))
							.timerCreDay(timerRecodeInfoToday.getTimerCreDay())
							.build()
			);
			TimerResponseDTO.RecodeTime recodeTime = t.toDTO();
			return new ResponseEntity<>(recodeTime, HttpStatus.OK);
		} else {
			log.info("중복저장");
			Timer t = findT.get().updateRecodeInfo(timeInfoToStringFormat(timerRecodeInfoToday), sumOfDayTime(timerRecodeInfoToday));
			TimerResponseDTO.RecodeTime recodeTime = t.toDTO();
			return new ResponseEntity<>(recodeTime, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getTodayInfo(TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(todayDateInfoDTO.getOauthId());
		Optional<Timer> findT = timerRepository.findByTimerCreDayAndMemberSocial(todayDateInfoDTO.getTimerCreDay(), findM);
		if (findT.isPresent()){
			return new ResponseEntity<>(findT.get().toTimeInfo(),HttpStatus.OK);
		}else {
			return new ResponseEntity<>("해당날짜 공부 기록없음",HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getDiffYesterdayInfo(TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(diffYesterdayDateCompareDTO.getOauthId());
		Optional<Timer> findT = timerRepository.findByTimerCreDayAndMemberSocial(diffYesterdayDateCompareDTO.getTodayDateInfo(), findM);
		if (findT.isEmpty()){
			return new ResponseEntity<>("해당 날짜 기록이 없습니다",HttpStatus.OK);
		}
		long todayRecode = findT.get().getDay_of_totalTime();
		if (timerRepository.findByTimerCreDayAndMemberSocial(diffYesterdayDateCompareDTO.getYesterdayDateInfo(),findM).isEmpty()){
			return new ResponseEntity<>("오늘이 첫 공부",HttpStatus.OK);
		}else {
			long yesterDayRecode= timerRepository.findByTimerCreDayAndMemberSocial(diffYesterdayDateCompareDTO.getYesterdayDateInfo(), findM)
					.get().getDay_of_totalTime();
			return calculateTimeDiff(todayRecode,yesterDayRecode);
		}
	}


	private int sumOfDayTime(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		int hours_sec = Integer.parseInt(timerRecodeInfoToday.getHours()) * 60 * 60;
		int minute_sec = Integer.parseInt(timerRecodeInfoToday.getMinute()) * 60;
		int sec_sec = Integer.parseInt(timerRecodeInfoToday.getSecond());

		return hours_sec+minute_sec+sec_sec;
	}

	private String timeInfoToStringFormat(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		return timerRecodeInfoToday.getHours()
				+ ":" +timerRecodeInfoToday.getMinute()
				+ ":" +timerRecodeInfoToday.getSecond();
	}

	private ResponseEntity<?> calculateTimeDiff(long todayRecode,long yesterdayRecode){
		if (todayRecode>yesterdayRecode){
			long diffRecode = todayRecode - yesterdayRecode;
			String cal_result = caluculateResult(diffRecode);
			return new ResponseEntity<>( "+"+cal_result,HttpStatus.OK);
		}else if (yesterdayRecode==todayRecode){
			return new ResponseEntity<>("=",HttpStatus.OK);
		}else {
			long diffRecode = yesterdayRecode - todayRecode;
			String cal_result = caluculateResult(diffRecode);
			return new ResponseEntity<>("-"+cal_result,HttpStatus.OK);
		}
	}

	private String caluculateResult(long diffRecode){
		long hours = diffRecode / 3600;
		long minutes = (diffRecode % 3600) / 60;
		long seconds = diffRecode % 60;

		return String.format("%d시간 %d분 %d초", hours, minutes, seconds);
	}

	private String changeTimeFormatToString(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		return timerRecodeInfoToday.getHours()+":"
				+timerRecodeInfoToday.getMinute()+":"
				+timerRecodeInfoToday.getSecond();
	}

	@Override
	public ResponseEntity<?> getDiffWeekInfo(String oauthId){
		QTimer timer = QTimer.timer;
		LocalDate today = LocalDate.now();
		LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate endOfWeek = startOfWeek.plusDays(6);

		// 회원 정보 가져오기
		MemberSocial memberInfo = memberService.getMemberInfoByOAuthId(oauthId);
		List<TimerResponseDTO.RecodeTime> diffWeekInfoListToDTO=new ArrayList<>();
		List<Timer> diffWeekInfoList = jpaQueryFactory.selectFrom(timer)
				.where(timer.memberSocial.eq(memberInfo)
						.and(timer.timerCreDay.between(startOfWeek, endOfWeek)
						))
				.orderBy(timer.timerCreDay.asc())
				.fetch();


		if (diffWeekInfoList.isEmpty()){
			return new ResponseEntity<>("이번 주 공부 데이터 없음"+diffWeekInfoList,HttpStatus.OK);
		}else {
			for (Timer t: diffWeekInfoList){
				diffWeekInfoListToDTO.add(t.toDTO());
			}
			return new ResponseEntity<>(diffWeekInfoListToDTO,HttpStatus.OK);
		}
	}

	@Override
	public List<Timer> getTimerAllInfo() {
		return timerRepository.findAll();
	}

	/*private void getTimerReward(MemberSocial memberSocial){
		switch (timerRepository.findByMemberSocial(memberSocial)
				.get()
				.size()){
			case 1:
				rewardMemberSocialCheckService.saveRewardMemberSocial("부서진 초시계(을)를 얻었다",memberSocial);
				break;
			case 50:
				rewardMemberSocialCheckService.saveRewardMemberSocial("초시계(750G) 획득!",memberSocial);
				break;
			case 100:
				rewardMemberSocialCheckService.saveRewardMemberSocial("");
		}
	}*/
}
