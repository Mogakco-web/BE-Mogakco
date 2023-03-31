package project.mogakco.domain.ranking.application.service;

import project.mogakco.domain.ranking.entity.redis.RankingRedis;

import java.util.List;

public interface RankingService {

	void recodeTimeOfMemberRankingInit();

	List<RankingRedis> getListInfoRanking();
}
