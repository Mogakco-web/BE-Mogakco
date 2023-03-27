package project.mogakco.domain.ranking.application.service;

import project.mogakco.domain.ranking.dto.response.RankingResponseDTO;
import project.mogakco.domain.ranking.entity.Ranking;

import java.util.List;

public interface RankingService {

	void recodeTimeOfMemberRankingInit();

	List<RankingResponseDTO> getListInfoRanking();
}
