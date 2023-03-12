package project.mogakco.domain.todo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.mogakco.domain.todo.application.service.todo.ToDoService;
import project.mogakco.domain.todo.dto.request.ToDoDTO;
import project.mogakco.domain.todo.entity.ToDo;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/todo")
public class ToDoAPIController {

	private final ToDoService toDoService;

	@PostMapping
	public ResponseEntity<?> createOneTodoTap(@RequestBody ToDoDTO.ToDoCreateDTO toDoCreateDTO){
		ToDo createTodo = toDoService.createOneToDoTap(toDoCreateDTO);
		return new ResponseEntity<>(createTodo, HttpStatus.OK);
	}

	@PostMapping("/contents")
	public ResponseEntity<?> writeContents(@RequestBody ToDoDTO.ToDoWriteContentsDTO writeContentsDTO){
		ToDo writeToDO = toDoService.writeContentsOneToDoTap(writeContentsDTO);
		return new ResponseEntity<>(writeContentsDTO,HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> changeTitleTodo(@RequestBody ToDoDTO.ChangTitleDTO changTitleDTO){
		ToDo changeTitle = toDoService.changeTitleTodo(changTitleDTO);
		return new ResponseEntity<>(changeTitle,HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> eliminate(@RequestBody ToDoDTO.ToDoEliminateDTO eliminateDTO){
		return toDoService.eliminateOneToDoTap(eliminateDTO);
	}

}
