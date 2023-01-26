package project.mogakco.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.member.entity.MemberSocial;

public interface MemberRepository extends JpaRepository<MemberSocial,Long> {
}
