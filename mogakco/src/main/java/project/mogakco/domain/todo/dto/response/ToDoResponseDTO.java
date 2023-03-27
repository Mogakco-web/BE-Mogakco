package project.mogakco.domain.todo.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ToDoResponseDTO {

	public Long todoSeq;

	public String todoTitle;

	public String todoContents;

	public Long categoryId;

	public Long memberId;

}
