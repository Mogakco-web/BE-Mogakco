package project.mogakco.domain.todo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.global.dto.requset.MemberInfoDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryAPIController {

	private final MemberServiceImpl memberService;

	private final CategoryService categoryService;

	@GetMapping()
	public void getCategoryListInfo(@RequestBody MemberInfoDTO.selectInfoByoauthIdDTO selectInfoByoauthIdDTO){
		categoryService.getListOfCategory(memberService.getMemberInfoByOAuthId(selectInfoByoauthIdDTO.getOauthId()));
	}
}
