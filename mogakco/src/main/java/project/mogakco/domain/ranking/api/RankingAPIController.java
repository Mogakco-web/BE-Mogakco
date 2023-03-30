package project.mogakco.domain.ranking.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.ranking.application.service.RankingService;
import project.mogakco.domain.ranking.dto.response.RankingResponseDTO;
import project.mogakco.domain.ranking.entity.redis.RankingRedis;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ranking")
@Log4j2
public class RankingAPIController {

	private final RankingService rankingService;

	@PostMapping
	public void getRankingInfoInit() {
		System.out.println("랭킹 입장");
		rankingService.recodeTimeOfMemberRankingInit();
	}

	@GetMapping
	public ResponseEntity<?> getRankingInfoList() {
		List<RankingRedis> listInfoRanking = rankingService.getListInfoRanking();
		if (listInfoRanking.isEmpty()) {
			return new ResponseEntity<>("아무도 시간 기록없음", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(listInfoRanking, HttpStatus.OK);
		}
	}
}
