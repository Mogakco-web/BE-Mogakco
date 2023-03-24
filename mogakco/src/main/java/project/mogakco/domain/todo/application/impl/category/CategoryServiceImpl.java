package project.mogakco.domain.todo.application.impl.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.domain.todo.dto.CategoryResponseDTO;
import project.mogakco.domain.todo.dto.request.CategoryDTO;
import project.mogakco.domain.todo.dto.response.ToDoResponseDTO;
import project.mogakco.domain.todo.entity.Category;
import project.mogakco.domain.todo.entity.QCategory;
import project.mogakco.domain.todo.entity.ToDo;
import project.mogakco.domain.todo.repo.CategoryRepository;

import java.util.*;

@Service
@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final ArrayList<String> basicCategories= new ArrayList<>(Arrays.asList("ToDo","Doing","Done"));

	private final CategoryRepository categoryRepository;

	private final MemberServiceImpl memberService;

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	@Transactional
	public ResponseEntity<?> createCategoryOne(CategoryDTO.CategoryCreateDTO categoryCreateDTO) {
		Category saveCategory = categoryRepository.save(
				Category.builder()
						.memberSocial(memberService.getMemberInfoByOAuthId(categoryCreateDTO.getOauthId()))
						.categoryName(categoryCreateDTO.getCategory_name())
						.build()
		);
		CategoryResponseDTO categoryResponseDTO = saveCategory.toDTO();
		return new ResponseEntity<>(categoryResponseDTO,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getListOfCategory(MemberSocial memberSocial) {
		QCategory category=QCategory.category;

		List<Category> findList = jpaQueryFactory.selectFrom(category)
				.where(category.memberSocial.eq(memberSocial))
				.fetch();
		return new ResponseEntity<>(getInfoMemberCategory(findList),HttpStatus.OK);
	}

	@Override
	public Category getCategoryInfoNameAndMember(String category_name,MemberSocial member) {
		return categoryRepository.findByCategoryNameAndMemberSocial(category_name,member).orElse(null);
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
		return categoryRepository.findByCategoryNameAndMemberSocial("ToDo",memberSocial).isPresent();
	}

	@Override
	@Transactional
	public ResponseEntity<?> changeCategorayName(CategoryDTO.ChangeNameDTO changeNameDTO) {
		CategoryResponseDTO categoryResponseDTO =
				categoryRepository.findByCategoryNameAndMemberSocial(changeNameDTO.getCategoryOwn(),
								memberService.getMemberInfoByOAuthId(changeNameDTO.getOauthId()))
						.get()
							.changeCategoryName(changeNameDTO.getCategoryGeu())
								.toDTO();
		return new ResponseEntity<>(categoryResponseDTO,HttpStatus.OK);
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

	private List<CategoryResponseDTO.ListOfMemberCategory> getInfoMemberCategory(List<Category> categories){
		List<CategoryResponseDTO.ListOfMemberCategory> getInfoMemberCategoryList=new ArrayList<>();
		for (int i=0;i<categories.size();i++){
			CategoryResponseDTO.ListOfMemberCategory listOfMemberCategory = categories.get(i).toListOfCategory();
			listOfMemberCategory.setTodoList(getToDoInfoToDTO(categories.get(i).getToDo()));
			getInfoMemberCategoryList.add(listOfMemberCategory);
		}
		return getInfoMemberCategoryList;
	}

	private List<ToDoResponseDTO> getToDoInfoToDTO(List<ToDo> toDoList){
		Set<ToDoResponseDTO> toDoResponseDTOList=new HashSet<>();
		for(ToDo t:toDoList){
			toDoResponseDTOList.add(t.toDTO());
		}
		return new ArrayList<>(toDoResponseDTOList);
	}
}
