package project.mogakco.domain.ranking.repo.redis;

import org.springframework.data.repository.CrudRepository;
import project.mogakco.domain.ranking.entity.redis.RankingRedis;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RankingRedisRepository extends CrudRepository<RankingRedis, String> {
	Optional<RankingRedis> findByUserNickname(String userNickName);
}
