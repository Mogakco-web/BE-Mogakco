package project.mogakco.domain.mypage.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.mypage.entity.Reward;

public interface RewardRepository extends JpaRepository<Reward,Long> {

}
