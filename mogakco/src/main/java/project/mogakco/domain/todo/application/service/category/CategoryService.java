package project.mogakco.domain.todo.application.service.category;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.dto.request.CategoryDTO;
import project.mogakco.domain.todo.entity.Category;

import java.util.List;

public interface CategoryService {

	Category createCategoryOne(CategoryDTO.CategoryCreateDTO categoryCreateDTO);

	List<Category> getListOfCategory(MemberSocial memberSocial);

	Category getCategoryInfoNameAndMember(String category_name,MemberSocial memberSocial);

	void initializeBasicCategory(MemberSocial member);

	boolean getCategoryInfoNameAndMember(MemberSocial memberSocial);

	Category changeCategorayName(CategoryDTO.ChangeNameDTO changeNameDTO);

	ResponseEntity<?> eliminateCategory(CategoryDTO.EliminateDTO eliminateDTO);
}
