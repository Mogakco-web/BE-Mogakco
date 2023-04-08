package project.mogakco.domain.mypage.application.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.application.service.MyPageService;
import project.mogakco.domain.mypage.dto.MyPageDTO;
import project.mogakco.domain.timer.entity.QTimer;
import project.mogakco.domain.timer.entity.Timer;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

	private final JPAQueryFactory jpaQueryFactory;

	private final MemberServiceImpl memberService;

	@Override
	public ResponseEntity<?> getTotalTimerUse(MyPageDTO.totalTimerUse totalTimerUse) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(totalTimerUse.getOauthId());
		QTimer timer = QTimer.timer;
		Long memberOfTotal = jpaQueryFactory.select(timer.day_of_totalTime.sum())
				.from(timer)
				.where(timer.memberSocial.eq(findM))
				.fetchOne();
		if (memberOfTotal == null) {
			return new ResponseEntity<>("공부 기록 없음", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(totalTimeToTimerFormat(memberOfTotal), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> continueTimerDay(MyPageDTO.continueTimer continueTimer) {
		MemberSocial findM = memberService.getMemberInfoByOAuthId(continueTimer.getOauthId());
		QTimer timer = QTimer.timer;
		List<Timer> fetch = jpaQueryFactory.selectFrom(timer)
				.where(timer.memberSocial.eq(findM)
						.and(timer.timerCreDay.before(continueTimer.getNowDate())))
				.orderBy(timer.timerCreDay.desc())
				.fetch();

		int count = 0;
		LocalDate prevDate = null;
		for (Timer t : fetch) {
			if (prevDate == null || t.getTimerCreDay().plusDays(1).isEqual(prevDate)) {
				prevDate = t.getTimerCreDay();
				count++;
			} else {
				break;
			}
		}
		return new ResponseEntity<>(count, HttpStatus.OK);
	}

	private String totalTimeToTimerFormat ( long memberOfTotal){
		long seconds = memberOfTotal % 60;
		long minutes = (memberOfTotal / 60) % 60;
		long hours = memberOfTotal / 3600;

		return String.format("%d일 %02d시간 %02d분 %02d초", hours / 24, hours % 24, minutes, seconds);
	}
}
