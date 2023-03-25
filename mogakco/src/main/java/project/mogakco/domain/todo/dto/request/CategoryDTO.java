package project.mogakco.domain.todo.dto.request;

import lombok.Getter;

@Getter
public class CategoryDTO {

	@Getter
	public static class CategoryCreateDTO{
		private String oauthId;
		private String category_name;
	}

	@Getter
	public static class ChangeNameDTO{
		private String oauthId;
		private String categoryOwn;
		private String categoryGeu;
	}

	@Getter
	public static class EliminateDTO{
		private String oauthId;
		private Long categorySeq;
	}
}
