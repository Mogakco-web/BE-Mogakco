package project.mogakco.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.member.entity.MemberSocial;
import project.mogakco.domain.member.entity.SocialType;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberSocial,Long> {
	Optional<MemberSocial> findByOauthId(String id);
	Optional<MemberSocial> findByEmail(String email);
	Optional<MemberSocial> findByRefreshToken(String refreshToken);

	Optional<MemberSocial> findBySocialTypeAndOauthId(SocialType socialType, String oauthId);
}
