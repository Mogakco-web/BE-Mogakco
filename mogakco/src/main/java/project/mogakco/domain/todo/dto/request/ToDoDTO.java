package project.mogakco.domain.todo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ToDoDTO {

	@Getter
	public static class ToDoCreateDTO{
		private String todo_title;
		private String category_name;
	}

	@Getter
	public static class ToDoWriteContentsDTO{
		private Long todo_seq;
		private String todo_contents;
	}

	@Getter
	public static class ToDoEliminateDTO {
		private Long todo_seq;
	}
}
