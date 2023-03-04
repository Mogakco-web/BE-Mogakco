package project.mogakco.domain.todo.application.impl.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.domain.todo.entity.Category;
import project.mogakco.domain.todo.repo.CategoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final ArrayList<String> basicCategories= new ArrayList<>(Arrays.asList("ToDo","Doing","Done"));

	private final CategoryRepository categoryRepository;

	@Override
	@Transactional
	public Category createCategoryOne(String category_name) {
		return categoryRepository.save(
				Category.builder()
						.categoryName(category_name)
						.build()
		);

	}

	@Override
	public List<Category> getListOfCategory(MemberSocial memberSocial) {
		List<Category> findByMemberInfoCategoryList=new ArrayList<>();
		for (Category category:categoryRepository.findAll()){
			if (category.getMemberSocial().equals(memberSocial)) findByMemberInfoCategoryList.add(category);
		}

		return findByMemberInfoCategoryList;
	}

	@Override
	public Category getCategoryInfoName(String category_name) {
		return categoryRepository.findByCategoryName(category_name).orElse(null);
	}

	@Override
	@Transactional
	public void initializeBasicCategory(MemberSocial member) {
		for (String category:basicCategories){
			categoryRepository.save(
					Category.builder()
							.categoryName(category)
							.memberSocial(member)
							.build()
			);
		}
	}

	@Override
	public boolean getCategoryInfoNameAndMember(MemberSocial memberSocial) {
		return categoryRepository.findByCategoryNameAndMemberSocial("ToDo",memberSocial).isEmpty();
	}

}
