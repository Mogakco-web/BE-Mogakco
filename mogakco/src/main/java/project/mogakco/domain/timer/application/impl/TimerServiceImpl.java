package project.mogakco.domain.timer.application.impl;

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
import project.mogakco.domain.timer.entity.Timer;
import project.mogakco.domain.timer.repo.TimerRepository;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Service
@Transactional(readOnly = true)
public class TimerServiceImpl implements TimerService {

	private final TimerRepository timerRepository;

	private final MemberServiceImpl memberService;

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
			Timer t = findT.get().updateRecodeInfo(timeInfoToStringFormat(findT.get().getRecodeTime(),timerRecodeInfoToday), sumOfDayTime(timerRecodeInfoToday));
			TimerResponseDTO.RecodeTime recodeTime = t.toDTO();
			return new ResponseEntity<>(recodeTime,HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getTodayInfo(TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(todayDateInfoDTO.getOauthId());
		Timer timer = timerRepository.findByCreateDateAndMemberSocial(todayDateInfoDTO.getLocalDate(),findM).get();
		return new ResponseEntity<>(timer.getDay_of_totalTime(),HttpStatus.OK);
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

	private String timeInfoToStringFormat(String own_recode,TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		String[] hms = own_recode.split(":");
		int hours = Integer.parseInt(hms[0]) + Integer.parseInt(timerRecodeInfoToday.getHours());
		int minute = Integer.parseInt(hms[1]) + Integer.parseInt(timerRecodeInfoToday.getMinute());
		int second = Integer.parseInt(hms[2]) + Integer.parseInt(timerRecodeInfoToday.getSecond());

		return calculateTotalTimeToFormat(hours,minute,second);
	}

	private ResponseEntity<?> calculateTimeDiff(long todayRecode,long yesterdayRecode){
		if (todayRecode>yesterdayRecode){
			long diffRecode = todayRecode - yesterdayRecode;
			String cal_result = caluculateResult(diffRecode);
			return new ResponseEntity<>( cal_result+" 더 공부했습니다.",HttpStatus.OK);
		}else if (yesterdayRecode==todayRecode){
			return new ResponseEntity<>("어제랑 같은 시간으로 공부했네요!",HttpStatus.OK);
		}else {
			long diffRecode = yesterdayRecode - todayRecode;
			String cal_result = caluculateResult(diffRecode);
			return new ResponseEntity<>(cal_result+"가 어제보다 부족하네요",HttpStatus.OK);
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

/*	public ResponseEntity<?> getDiffWeekInfo(){

	}*/

	private String calculateTotalTimeToFormat(int h,int m,int s){
		if (s>=60){
			m+=s/60;
			s%=60;
		}
		if (m>=60){
			h=m/60;
			m%=60;
		}
		return h
				+ ":" +m
						+ ":" + s;
	}
}
