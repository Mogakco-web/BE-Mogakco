package project.mogakco.domain.todo.application.impl.todo;

import com.sun.xml.bind.v2.TODO;
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
import project.mogakco.domain.todo.dto.response.ToDoResponseDTO;
import project.mogakco.domain.todo.entity.Category;
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
	public ToDoResponseDTO createOneToDoTap(ToDoDTO.ToDoCreateDTO toDoCreateDTO){

		Category categoryInfoName = categoryService.getCategoryInfoName(toDoCreateDTO.getCategoryName());
		ToDo createdTodo = ToDo
							.builder()
							.todoTitle(toDoCreateDTO.getTodoTitle())
							.category(categoryInfoName)
							.memberSocial(memberService.getMemberInfoByOAuthId(toDoCreateDTO.getOauthId()))
							.build();



		return toDoRepository.save(createdTodo).toDTO();
	}

	@Transactional
	@Override
	public ResponseEntity<?> writeContentsOneToDoTap(ToDoDTO.ToDoWriteContentsDTO toDoWriteContentsDTO){
		/*return toDoRepository.findByTodoSeqAndMemberSocial(toDoWriteContentsDTO.getTodoSeq(),
						memberService.getMemberInfoByOAuthId(toDoWriteContentsDTO.getOauthId()))
							.get()
								.writeContents(toDoWriteContentsDTO.getTodo_contents());*/
		Optional<ToDo> findTo = toDoRepository.findByTodoSeqAndMemberSocial(toDoWriteContentsDTO.getTodoSeq(),
				memberService.getMemberInfoByOAuthId(toDoWriteContentsDTO.getOauthId()));
		if (findTo.isPresent()){
			return new ResponseEntity<>(findTo.get().writeContents(toDoWriteContentsDTO.getTodo_contents()).toDTO(),HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Request Not Accept",HttpStatus.BAD_REQUEST);
		}
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
	public ToDoResponseDTO changeTitleTodo(ToDoDTO.ChangTitleDTO changTitleDTO) {
		Optional<ToDo> findT =
				toDoRepository.findByTodoSeqAndMemberSocial(
						changTitleDTO.getTodoSeq(),
						memberService.getMemberInfoByOAuthId(changTitleDTO.getOauthId())
				);

		return findT.map(toDo -> toDo.changeTitleTodo(changTitleDTO.getChangeTitle())).orElse(null).toDTO();
	}

}
