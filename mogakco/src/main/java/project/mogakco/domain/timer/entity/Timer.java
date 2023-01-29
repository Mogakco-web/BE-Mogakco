package project.mogakco.domain.timer.entity;

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
}
