package project.mogakco.domain.todo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.todo.application.service.todo.ToDoService;
import project.mogakco.domain.todo.dto.request.ToDoDTO;
import project.mogakco.domain.todo.dto.response.ToDoResponseDTO;
import project.mogakco.domain.todo.entity.ToDo;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/todo")
public class ToDoAPIController {

	private final ToDoService toDoService;

	@PostMapping("/create")
	public ToDoResponseDTO createOneTodoTap(@RequestBody ToDoDTO.ToDoCreateDTO toDoCreateDTO){
		System.out.println("TODOCREATE===================");
		System.out.println(toDoCreateDTO.getCategoryName());
		System.out.println(toDoCreateDTO.getOauthId());
		System.out.println(toDoCreateDTO.getTodoTitle());
		ToDoResponseDTO responseDTO = toDoService.createOneToDoTap(toDoCreateDTO);
		return responseDTO;
//		return new ResponseEntity<>(createdToDo,HttpStatus.OK);
		/*ToDoResponseDTO createdTodo = toDoService.createOneToDoTap(toDoCreateDTO);
		return new ResponseEntity<>(createdTodo, HttpStatus.OK);*/
	}

	@PostMapping("/contents")
	public ResponseEntity<?> writeContents(@RequestBody ToDoDTO.ToDoWriteContentsDTO writeContentsDTO){
		return toDoService.writeContentsOneToDoTap(writeContentsDTO);
	}

	@PutMapping
	public ResponseEntity<?> changeTitleTodo(@RequestBody ToDoDTO.ChangTitleDTO changTitleDTO){
		ToDoResponseDTO responseDTO = toDoService.changeTitleTodo(changTitleDTO);
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> eliminate(@RequestBody ToDoDTO.ToDoEliminateDTO eliminateDTO){
		return toDoService.eliminateOneToDoTap(eliminateDTO);
	}

}
