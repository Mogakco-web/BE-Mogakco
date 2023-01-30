package project.mogakco.domain.timer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.timer.entity.Timer;

public interface TimerRepository extends JpaRepository<Timer,Long> {
}
