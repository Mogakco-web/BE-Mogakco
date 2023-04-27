package project.mogakco.domain.mypage.entity;

import lombok.*;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.mypage.dto.response.RewardDTO;

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

	private String description;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "reward")
	private List<RewardMemberSocial> rewardMemberSocials;

	public RewardDTO.listReward toDTO(){
		return RewardDTO.listReward.builder()
				.rewardSeq(rewardSeq)
				.name(name)
				.description(description)
				.build();
	}

	public RewardDTO.checkReward toCheckDTO(){
		return RewardDTO.checkReward.builder()
				.rewardSeq(rewardSeq)
				.name(name)
				.description(description)
				.build();
	}
}
