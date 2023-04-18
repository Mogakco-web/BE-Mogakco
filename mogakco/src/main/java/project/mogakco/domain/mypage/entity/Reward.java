package project.mogakco.domain.mypage.entity;

import lombok.*;
import project.mogakco.domain.member.entity.member.MemberSocial;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Reward {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rewardSeq;

	private String name;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "reward")
	private List<RewardMemberSocial> rewardMemberSocials;

}
