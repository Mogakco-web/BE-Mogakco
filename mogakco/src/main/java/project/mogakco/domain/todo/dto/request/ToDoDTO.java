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
}
