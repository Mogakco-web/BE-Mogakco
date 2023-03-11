package project.mogakco.domain.todo.application.service.todo;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.todo.dto.request.ToDoDTO;
import project.mogakco.domain.todo.entity.ToDo;

import java.util.List;

public interface ToDoService {

	ToDo createOneToDoTap(ToDoDTO.ToDoCreateDTO toDoCreateDTO);

	ToDo writeContentsOneToDoTap(ToDoDTO.ToDoWriteContentsDTO toDoWriteContentsDTO);

	ResponseEntity<?> eliminateOneToDoTap(ToDoDTO.ToDoEliminateDTO toDoEliminateDTO);

	ToDo changeTitleTodo(ToDoDTO.ChangTitleDTO changTitleDTO);
}
