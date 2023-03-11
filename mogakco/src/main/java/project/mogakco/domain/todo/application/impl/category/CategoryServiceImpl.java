package project.mogakco.domain.todo.application.impl.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.domain.todo.dto.request.CategoryDTO;
import project.mogakco.domain.todo.entity.Category;
import project.mogakco.domain.todo.repo.CategoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final ArrayList<String> basicCategories= new ArrayList<>(Arrays.asList("ToDo","Doing","Done"));

	private final CategoryRepository categoryRepository;

	private final MemberServiceImpl memberService;

	@Override
	@Transactional
	public Category createCategoryOne(CategoryDTO.CategoryCreateDTO categoryCreateDTO) {
		return categoryRepository.save(
				Category.builder()
						.memberSocial(memberService.getMemberInfoByOAuthId(categoryCreateDTO.getOauthId()))
						.categoryName(categoryCreateDTO.getCategory_name())
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

	@Override
	@Transactional
	public Category changeCategorayName(CategoryDTO.ChangeNameDTO changeNameDTO) {
		return categoryRepository.findByCategoryNameAndMemberSocial(changeNameDTO.getCategoryOwn(),
						memberService.getMemberInfoByOAuthId(changeNameDTO.getOauthId()))
						.get()
							.changeCategoryName(changeNameDTO.getCategoryGeu());
	}

	@Override
	@Transactional
	public ResponseEntity<?> eliminateCategory(CategoryDTO.EliminateDTO eliminateDTO) {
		Optional<Category> findC = categoryRepository.findByCategoryNameAndMemberSocial(eliminateDTO.getCategoryName(),
				memberService.getMemberInfoByOAuthId(eliminateDTO.getOauthId()));

		if (findC.isPresent()){
			categoryRepository.delete(findC.get());
			return new ResponseEntity<>("Category Delete", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Bad Req",HttpStatus.BAD_REQUEST);
		}
	}

}
