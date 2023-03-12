package project.mogakco.domain.todo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mogakco.domain.member.entity.member.MemberSocial;

@Getter
@Setter
@Builder
public class ToDoResponseDTO {

	public Long todoSeq;

	public String todoTitle;

	public String todoContents;

	public Long categoryId;

	public Long memberId;

	public ToDoResponseDTO(Long todoSeq, String todoTitle, String todoContents, Long categoryId, Long memberId) {
		this.todoSeq = todoSeq;
		this.todoTitle = todoTitle;
		this.todoContents = todoContents;
		this.categoryId = categoryId;
		this.memberId = memberId;
	}
}
