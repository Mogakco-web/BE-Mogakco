package project.mogakco.domain.mypage.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.entity.Reward;
import project.mogakco.domain.mypage.entity.RewardMemberSocial;

import java.util.List;
import java.util.Optional;


public interface RewardMemberSocialRepository extends JpaRepository<RewardMemberSocial,Long> {
	Optional<RewardMemberSocial> findByMemberSocialAndRewardName(MemberSocial m,String name);
	List<RewardMemberSocial> findByMemberSocial(MemberSocial m);
}
