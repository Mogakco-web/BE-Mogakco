package project.mogakco.domain.timer.entity;

import lombok.*;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.global.domain.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Timer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long timer_seq;

	@ManyToOne(fetch = FetchType.LAZY)
	private MemberSocial memberSocial;

	private String recodeTime;

	private long day_of_totalTime;

	public Timer updateRecodeInfo(String recodeTime, long day_of_totalTime){
		this.recodeTime=recodeTime;
		this.day_of_totalTime+=day_of_totalTime;
		return this;
	}
}
