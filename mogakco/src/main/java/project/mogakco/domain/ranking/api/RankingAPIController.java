package project.mogakco.domain.ranking.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.ranking.application.service.RankingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ranking")
@Log4j2
public class RankingAPIController {

	private final RankingService rankingService;

	@GetMapping
	public void getRankingInfoInit(){
		System.out.println("랭킹 입장");
		rankingService.recodeTimeOfMemberRankingInit();
	}
}
