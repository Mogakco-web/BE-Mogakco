package project.mogakco.domain.timer.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.mogakco.domain.member.application.impl.GithubSocialServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.dto.request.TimerRecodeDTO;
import project.mogakco.domain.timer.entity.Timer;
import project.mogakco.domain.timer.repo.TimerRepository;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class TimerServiceImpl implements TimerService {

	private final TimerRepository timerRepository;

	private final GithubSocialServiceImpl githubSocialService;

	@Override
	public ResponseEntity<?> recodeTimeToday(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday) {
		MemberSocial findM = githubSocialService.findByOAuthId(timerRecodeInfoToday.getUser_oauthId());
		Optional<Timer> findT = timerRepository.findByCreateDateAndMemberSocial(timerRecodeInfoToday.getLocalDate(),findM);
		if (findT.isEmpty()){
			Timer t = timerRepository.save(
					Timer.builder()
							.recodeTime(changeTimeFormatToString(timerRecodeInfoToday))
							.memberSocial(findM)
							.day_of_totalTime(sumOfDayTime(timerRecodeInfoToday))
							.build()
			);
			return new ResponseEntity<>(t, HttpStatus.OK);
		}else {
			Timer t = findT.get().updateRecodeInfo(timeInfoToStringFormat(timerRecodeInfoToday), sumOfDayTime(timerRecodeInfoToday));
			return new ResponseEntity<>(t,HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getTodayInfo(TimerRecodeDTO.todayDateInfoDTO todayDateInfoDTO) {
		MemberSocial findM = githubSocialService.findByOAuthId(todayDateInfoDTO.getOauthId());
		Timer timer = timerRepository.findByCreateDateAndMemberSocial(todayDateInfoDTO.getLocalDate(),findM).get();
		return new ResponseEntity<>(timer.getDay_of_totalTime(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getDiffYesterdayInfo(TimerRecodeDTO.diffYesterdayDateCompareDTO diffYesterdayDateCompareDTO) {
		MemberSocial findM = githubSocialService.findByOAuthId(diffYesterdayDateCompareDTO.getOauthId());
		long todayRecode = timerRepository.findByCreateDateAndMemberSocial(diffYesterdayDateCompareDTO.getTodayDateInfo(), findM)
				.get().getDay_of_totalTime();
		long yesterDayRecode= timerRepository.findByCreateDateAndMemberSocial(diffYesterdayDateCompareDTO.getYesterdayDateInfo(), findM)
				.get().getDay_of_totalTime();
		return calculateTimeDiff(todayRecode,yesterDayRecode);
	}


	private int sumOfDayTime(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		int hours_sec = Integer.parseInt(timerRecodeInfoToday.getHours()) * 60 * 60;
		int minute_sec = Integer.parseInt(timerRecodeInfoToday.getMinute()) * 60;
		int sec_sec = Integer.parseInt(timerRecodeInfoToday.getSecond());

		return hours_sec+minute_sec+sec_sec;
	}

	private String timeInfoToStringFormat(TimerRecodeDTO.timerRecodeInfoToday timerRecodeInfoToday){
		String recodeTime=timerRecodeInfoToday.getHours()+":"+timerRecodeInfoToday.getMinute()+":"+timerRecodeInfoToday.getSecond();
		return recodeTime;
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
}
