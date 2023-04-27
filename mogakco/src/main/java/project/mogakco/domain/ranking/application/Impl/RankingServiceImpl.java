package project.mogakco.domain.ranking.application.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.ranking.application.service.RankingService;
import project.mogakco.domain.ranking.entity.redis.RankingRedis;
import project.mogakco.domain.ranking.repo.redis.RankingRedisRepository;
import project.mogakco.domain.timer.application.service.TimerCheckService;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.entity.Timer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

	private final JPAQueryFactory jpaQueryFactory;

	private final RankingRedisRepository rankingRedisRepository;

	private final TimerCheckService timerCheckService;

	@Override
	@Transactional
	public void recodeTimeOfMemberRankingInit() {
		rankingRedisRepository.deleteAll();
		Map<MemberSocial,Long> rankScore=new HashMap<>();
		LocalDate today = LocalDate.now();
		LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate endOfWeek = startOfWeek.plusDays(6);

		List<Timer> weekTimerList = timerCheckService.getTimerAllInfo()
				.stream()
				.filter(timer -> timer.getTimerCreDay().isAfter(startOfWeek.minusDays(1))
						&&
						timer.getTimerCreDay().isBefore(endOfWeek.plusDays(1)))
				.collect(Collectors.toList());

		for (Timer t: weekTimerList){
			rankScore.put(t.getMemberSocial(),rankScore.getOrDefault(t.getMemberSocial(),0L)+t.getDay_of_totalTime());
		}
		List<MemberSocial> sortedRankList = rankScore.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.limit(10)
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());

		int i=1;
		for (MemberSocial m:sortedRankList){
			System.out.println("User="+m.getNickname());
			rankingRedisRepository.save(
					RankingRedis.builder()
							.userNickname(m.getNickname())
							.rankingMember(m.toRankingDTO())
							.rank(i++)
							.createDate(LocalDate.now())
							.score(rankScore.get(m))
							.build()
			);
		}
	}


	@Override
	public List<RankingRedis> getListInfoRanking() {
		System.out.println("랭킹 리스트 불러오기");
		List<RankingRedis> rankInfoList = (List<RankingRedis>) rankingRedisRepository.findAll();
		if (rankInfoList.isEmpty()) System.out.println("리스트 비어있음");
		else System.out.println("안빔");
		System.out.println(rankInfoList.size());
		for (RankingRedis r: rankInfoList){
			System.out.println("rank="+r);
		}
		return rankInfoList.stream()
				.sorted(Comparator.comparingInt(RankingRedis::getRank))
				.collect(Collectors.toList());
	}
}
