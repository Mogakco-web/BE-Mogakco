package project.mogakco.domain.ranking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.ranking.entity.Ranking;

public interface RankingRepository extends JpaRepository<Ranking,Long> {
}
