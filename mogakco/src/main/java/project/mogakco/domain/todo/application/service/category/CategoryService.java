package project.mogakco.domain.todo.application.service.category;

import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.entity.Category;

import java.util.List;

public interface CategoryService {

	Category createCategoryOne(String category_name);

	List<Category> getListOfCategory(MemberSocial memberSocial);

	Category getCategoryInfoName(String category_name);

	void initializeBasicCategory(MemberSocial member);

	boolean getCategoryInfoNameAndMember(MemberSocial memberSocial);
}
