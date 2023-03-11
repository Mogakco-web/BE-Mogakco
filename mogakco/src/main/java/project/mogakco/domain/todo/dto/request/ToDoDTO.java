package project.mogakco.domain.todo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ToDoDTO {

	@Getter
	public static class ToDoCreateDTO{
		private String oauthId;
		private String todo_title;
		private String category_name;
	}

	@Getter
	public static class ToDoWriteContentsDTO{
		private Long todoSeq;
		private String todo_contents;
		private String oauthId;
	}

	@Getter
	public static class ToDoEliminateDTO {
		private Long todoSeq;
		private String oauthId;
	}

	@Getter
	public static class ChangTitleDTO {
		private Long todoSeq;
		private String oauthId;
		private String changeTitle;
	}
}
