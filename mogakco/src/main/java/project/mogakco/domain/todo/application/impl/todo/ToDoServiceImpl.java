package project.mogakco.domain.todo.application.impl.todo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.domain.todo.application.service.todo.ToDoService;
import project.mogakco.domain.todo.dto.request.ToDoDTO;
import project.mogakco.domain.todo.entity.ToDo;
import project.mogakco.domain.todo.repo.ToDoRepository;

@Service
@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

	private final CategoryService categoryService;

	private final ToDoRepository toDoRepository;

	private final MemberServiceImpl memberService;

	@Transactional
	public ToDo createOneToDoTap(ToDoDTO.ToDoCreateDTO toDoCreateDTO, String oauthId){
		return toDoRepository.save(
				ToDo.builder()
						.todo_title(toDoCreateDTO.getTodo_title())
						.category(categoryService.getCategoryInfoName(toDoCreateDTO.getCategory_name()))
						.memberSocial(memberService.getMemberInfoByOAuthId(oauthId))
						.build()
		);
	}

	public ToDo writeContentsOneToDoTap(Long todo_seq,String write_contents){
		return toDoRepository.findById(todo_seq).get().writeContents(write_contents);
	}
}
