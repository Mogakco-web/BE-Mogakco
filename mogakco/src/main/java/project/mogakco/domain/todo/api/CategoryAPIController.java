package project.mogakco.domain.todo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.domain.todo.dto.CategoryResponseDTO;
import project.mogakco.domain.todo.dto.request.CategoryDTO;
import project.mogakco.domain.todo.dto.request.ToDoDTO;
import project.mogakco.domain.todo.entity.Category;
import project.mogakco.global.dto.requset.MemberInfoDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryAPIController {

	private final MemberServiceImpl memberService;

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<?> getCategoryListInfo(@RequestBody MemberInfoDTO.selectInfoByoauthIdDTO selectInfoByoauthIdDTO){
		return categoryService.getListOfCategory(memberService.getMemberInfoByOAuthId(selectInfoByoauthIdDTO.getOauthId()));
	}

	@PostMapping
	public ResponseEntity<?> createCategorayByName(@RequestBody CategoryDTO.CategoryCreateDTO categoryCreateDTO){
		return categoryService.createCategoryOne(categoryCreateDTO);
	}

	@PutMapping
	public ResponseEntity<?> changCategoryName(@RequestBody CategoryDTO.ChangeNameDTO changeNameDTO){
		return categoryService.changeCategorayName(changeNameDTO);
	}

	@DeleteMapping
	public ResponseEntity<?> eliminateCategory(@RequestBody CategoryDTO.EliminateDTO eliminateDTO){
		return categoryService.eliminateCategory(eliminateDTO);
	}
}
