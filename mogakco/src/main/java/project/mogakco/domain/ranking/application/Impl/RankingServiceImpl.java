package project.mogakco.domain.ranking.application.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.ranking.application.service.RankingService;
import project.mogakco.domain.ranking.dto.response.RankingResponseDTO;
import project.mogakco.domain.ranking.entity.QRanking;
import project.mogakco.domain.ranking.entity.Ranking;
import project.mogakco.domain.ranking.repo.RankingRepository;
import project.mogakco.domain.timer.application.service.TimerService;
import project.mogakco.domain.timer.entity.Timer;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

	private final JPAQueryFactory jpaQueryFactory;

	private final TimerService timerService;

	private final RankingRepository rankingRepository;

	@Override
	@Transactional
	public void recodeTimeOfMemberRankingInit() {
		Map<MemberSocial,Long> rankScore=new HashMap<>();
		System.out.println("랭킹");
		for (Timer t:timerService.getTimerAllInfo()){
			rankScore.put(t.getMemberSocial(),rankScore.getOrDefault(t.getMemberSocial(),0L)+t.getDay_of_totalTime());
			System.out.println("member="+t.getMemberSocial().getNickname());
			System.out.println("RankScore="+rankScore.get(t.getMemberSocial()));
		}

		for (MemberSocial m:rankScore.keySet()){
			System.out.println(m.getNickname());
			Optional<Ranking> findR = rankingRepository.findByMemberSocial(m);
			if (findR.isEmpty()) {
				rankingRepository.save(
						Ranking.builder()
								.memberSocial(m)
								.score(rankScore.get(m))
								.build()
				);
			}
			else {
				findR.get().changeScoreInfo(rankScore.get(m));
			}
		}
		rankInitialize();
	}

	@Transactional
	public void rankInitialize(){
		QRanking ranking=QRanking.ranking;
		int i=1;
		List<Ranking> fetch = jpaQueryFactory.selectFrom(ranking)
				.orderBy(ranking.score.desc())
				.limit(10)
				.fetch();

		for (Ranking r:fetch){
			r.changeRankInfo(i);
			i++;
		}
	}

	@Override
	public List<RankingResponseDTO> getListInfoRanking() {
		return rankingRepository.findAll()
				.stream()
				.map(Ranking::toDTO)
				.sorted(Comparator.comparingInt(RankingResponseDTO::getRank))
				.collect(Collectors.toList());
	}
}
