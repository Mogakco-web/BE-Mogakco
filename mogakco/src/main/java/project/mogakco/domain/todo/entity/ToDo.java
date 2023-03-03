package project.mogakco.domain.todo.entity;

import lombok.*;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.global.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ToDo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long todo_seq;

	private String todo_title;

	private String todo_contents;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_seq")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private MemberSocial memberSocial;

	public ToDo writeContents(String todo_contents){
		this.todo_contents=todo_contents;
		return this;
	}
}
