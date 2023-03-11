package project.mogakco.domain.todo.application.impl.todo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.member.application.impl.MemberServiceImpl;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.domain.todo.application.service.todo.ToDoService;
import project.mogakco.domain.todo.dto.request.ToDoDTO;
import project.mogakco.domain.todo.entity.ToDo;
import project.mogakco.domain.todo.repo.ToDoRepository;

import java.util.Optional;

@Service
@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

	private final CategoryService categoryService;

	private final ToDoRepository toDoRepository;

	private final MemberServiceImpl memberService;

	@Transactional
	@Override
	public ToDo createOneToDoTap(ToDoDTO.ToDoCreateDTO toDoCreateDTO){
		return toDoRepository.save(
				ToDo.builder()
						.todoTitle(toDoCreateDTO.getTodo_title())
						.category(categoryService.getCategoryInfoName(toDoCreateDTO.getCategory_name()))
						.memberSocial(memberService.getMemberInfoByOAuthId(toDoCreateDTO.getOauthId()))
						.build()
		);
	}

	@Transactional
	@Override
	public ToDo writeContentsOneToDoTap(ToDoDTO.ToDoWriteContentsDTO toDoWriteContentsDTO){
		return toDoRepository.findByTodoSeqAndMemberSocial(toDoWriteContentsDTO.getTodoSeq(),
						memberService.getMemberInfoByOAuthId(toDoWriteContentsDTO.getOauthId()))
							.get()
								.writeContents(toDoWriteContentsDTO.getTodo_contents());
	}

	@Transactional
	@Override
	public ResponseEntity<?> eliminateOneToDoTap(ToDoDTO.ToDoEliminateDTO toDoEliminateDTO){
		Optional<ToDo> findT =
				toDoRepository.findByTodoSeqAndMemberSocial(
						toDoEliminateDTO.getTodoSeq(),
						memberService.getMemberInfoByOAuthId(toDoEliminateDTO.getOauthId())
				);

		if (findT.isPresent()){
			toDoRepository.delete(findT.get());
			return new ResponseEntity<>("todo_eliminate", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Bad sequence",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional
	public ToDo changeTitleTodo(ToDoDTO.ChangTitleDTO changTitleDTO) {
		Optional<ToDo> findT =
				toDoRepository.findByTodoSeqAndMemberSocial(
						changTitleDTO.getTodoSeq(),
						memberService.getMemberInfoByOAuthId(changTitleDTO.getOauthId())
				);

		return findT.map(toDo -> toDo.changeTitleTodo(changTitleDTO.getChangeTitle())).orElse(null);
	}


}
