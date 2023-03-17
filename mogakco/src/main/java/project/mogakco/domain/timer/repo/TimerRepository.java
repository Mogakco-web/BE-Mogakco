package project.mogakco.domain.timer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.timer.entity.Timer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimerRepository extends JpaRepository<Timer,Long> {
	Optional<Timer> findByCreateDateAndMemberSocial(LocalDate localDate, MemberSocial memberSocial);

	Optional<List<Timer>> findByMemberSocial(MemberSocial memberSocial);
}
