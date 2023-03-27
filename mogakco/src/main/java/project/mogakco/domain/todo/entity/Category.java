package project.mogakco.domain.todo.entity;

import lombok.*;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.dto.CategoryResponseDTO;
import project.mogakco.global.domain.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Category extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long category_seq;

	private String categoryName;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "category",cascade = CascadeType.ALL)
	private List<ToDo> toDo =new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private MemberSocial memberSocial;

	public Category changeCategoryName(String categoryName){
		this.categoryName=categoryName;
		return this;
	}

	public CategoryResponseDTO toDTO(){
		return CategoryResponseDTO.builder()
				.category_seq(category_seq)
				.categoryName(categoryName)
				.memberSocial(memberSocial)
				.build();
	}
}
