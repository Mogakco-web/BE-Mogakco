package project.mogakco.domain.timer.entity;

import lombok.Builder;
import project.mogakco.global.domain.BaseEntity;

import javax.persistence.*;

@Entity
public class Timer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long timer_seq;

	@OneToMany
	private MemberSocial memberSocial;

	private String recodeTime;

	@Builder
	public Timer(Long timer_seq, MemberSocial memberSocial, String recodeTime) {
		this.timer_seq = timer_seq;
		this.memberSocial = memberSocial;
		this.recodeTime = recodeTime;
	}
}
