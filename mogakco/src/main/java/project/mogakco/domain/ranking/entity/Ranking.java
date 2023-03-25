package project.mogakco.domain.ranking.entity;

import lombok.*;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.timer.entity.Timer;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Ranking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rankingSeq;

	private int rank;

	private long score;

	@OneToOne(fetch = FetchType.LAZY)
	private MemberSocial memberSocial;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Timer> timers;


	public void changeScoreInfo(Long changeInfoScore){
		this.score=changeInfoScore;
	}
}
