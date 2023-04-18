package project.mogakco.domain.todo.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.mogakco.domain.todo.entity.ToDo;

@Getter
@RequiredArgsConstructor
public class ToDoDTO {

	@Getter
	public static class ToDoCreateDTO{
		private String oauthId;
		private String todoTitle;
		private String categoryName;


		/*private ToDo toEntity() {
			return ToDo.builder()

					.build()
		}*/
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

	@Getter
	public static class ChangCategoryDTO{
		private Long todoSeq;
		private Long categorySeq;
	}
}
