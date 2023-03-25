package project.mogakco.domain.ranking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.ranking.entity.Ranking;

import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking,Long> {

	Optional<Ranking> findByMemberSocial(MemberSocial memberSocial);
}
