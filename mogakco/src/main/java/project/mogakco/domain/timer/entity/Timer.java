package project.mogakco.domain.timer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Timer {

	@Id
	@GeneratedValue
	private Long timer_seq;


}
