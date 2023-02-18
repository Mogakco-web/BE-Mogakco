package project.mogakco.domain.timer.entity;

import lombok.*;
import project.mogakco.global.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Timer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long timer_seq;

	@OneToMany
	private MemberSocial memberSocial;

	private String recodeTime;

	private long day_of_totalTime;
}
