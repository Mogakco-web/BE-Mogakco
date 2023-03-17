package project.mogakco.domain.timer.application.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.GithubSocialServiceImpl;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;
import project.mogakco.domain.timer.dto.response.TimerResponseDTO;
import project.mogakco.domain.timer.entity.QTimer;
import project.mogakco.domain.timer.entity.Timer;
import project.mogakco.domain.timer.repo.TimerRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Service
@Transactional(readOnly = true)
public class TimerServiceImpl implements TimerService {

	private final TimerRepository timerRepository;

	private final MemberServiceImpl memberService;

	private final JPAQueryFactory jpaQueryFactory;


	@Override
	@Transactional
	public ResponseEntity<?> recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(timerRecodeInfoToday.getOauthId());
		System.out.println(timerRecodeInfoToday.getLocalDate());
		Optional<Timer> findT = timerRepository.findByCreateDateAndMemberSocial(timerRecodeInfoToday.getLocalDate(),findM);
		if (findT.isEmpty()){
			Timer t = timerRepository.save(
					Timer.builder()
							.recodeTime(changeTimeFormatToString(timerRecodeInfoToday))
							.memberSocial(findM)
							.day_of_totalTime(sumOfDayTime(timerRecodeInfoToday))
							.build()
			);
			TimerResponseDTO.RecodeTime recodeTime = t.toDTO();
			return new ResponseEntity<>(recodeTime, HttpStatus.OK);
		}else {
			log.info("중복저장");
			Timer t = findT.get().updateRecodeInfo(timeInfoToStringFormat(timerRecodeInfoToday),sumOfDayTime(timerRecodeInfoToday));
			TimerResponseDTO.RecodeTime recodeTime = t.toDTO();
			return new ResponseEntity<>(recodeTime,HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getTodayInfo(TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(todayDateInfoDTO.getOauthId());
		Timer timer = timerRepository.findByCreateDateAndMemberSocial(todayDateInfoDTO.getLocalDate(),findM).get();
		return new ResponseEntity<>(timer.getRecodeTime(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getDiffYesterdayInfo(TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(diffYesterdayDateCompareDTO.getOauthId());
		long todayRecode = timerRepository.findByCreateDateAndMemberSocial(diffYesterdayDateCompareDTO.getTodayDateInfo(), findM)
				.get().getDay_of_totalTime();
		if (timerRepository.findByCreateDateAndMemberSocial(diffYesterdayDateCompareDTO.getYesterdayDateInfo(),findM).isEmpty()){
			return new ResponseEntity<>("오늘이 첫 공부",HttpStatus.OK);
		}else {
			long yesterDayRecode= timerRepository.findByCreateDateAndMemberSocial(diffYesterdayDateCompareDTO.getYesterdayDateInfo(), findM)
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
		diffRecode-=hours*3600;
		long minute = diffRecode / 60;
		diffRecode-=minute*60;
		long second=diffRecode;

		return hours+"시간"+minute+"분"+second+"초";
	}

	private String changeTimeFormatToString(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		return timerRecodeInfoToday.getHours()+":"
				+timerRecodeInfoToday.getMinute()+":"
				+timerRecodeInfoToday.getSecond();
	}

	@Override
	public void getDiffWeekInfo(String oauthId){
		log.info("1주일비교");
		System.out.println("1주일");
		QTimer timer=QTimer.timer;
		List<Timer> timers = timerRepository.findByMemberSocial(memberService.getMemberInfoByOAuthId(oauthId)).get();
		LocalDate today = LocalDate.now();
		LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate endOfWeek = startOfWeek.plusDays(6);

		for (Timer t:timers){
			List<Timer> fetch = jpaQueryFactory.selectFrom(timer)
					.where(timer.memberSocial.eq(t.getMemberSocial())
							.and(timer.createDate.between(startOfWeek, endOfWeek))
					)
					.fetch();
			System.out.println("fetch="+fetch);
		}

	}

}
