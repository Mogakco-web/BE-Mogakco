package project.mogakco.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.member.entity.member.AuthProvider;
import project.mogakco.domain.member.entity.member.MemberSocial;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberSocial,Long> {
	Optional<MemberSocial> findByEmailAndProvider(String email, AuthProvider provider);

}
