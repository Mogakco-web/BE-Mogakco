package project.mogakco.domain.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.entity.ToDo;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryResponseDTO {

	private Long category_seq;

	private String categoryName;

	private MemberSocial memberSocial;
}
