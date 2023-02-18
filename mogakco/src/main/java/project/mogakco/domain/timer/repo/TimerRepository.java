package project.mogakco.domain.timer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.timer.entity.Timer;

import java.time.LocalDate;
import java.util.Optional;

public interface TimerRepository extends JpaRepository<Timer,Long> {
	Optional<Timer> findByCreateDate(LocalDate localDate);
}
