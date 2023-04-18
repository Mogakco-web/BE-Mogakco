package project.mogakco.domain.mypage.entity;

import lombok.*;
import project.mogakco.domain.member.entity.member.MemberSocial;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RewardMemberSocial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rewardMemberSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rewardSeq")
	private Reward reward;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private MemberSocial memberSocial;
}
