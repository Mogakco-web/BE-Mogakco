package project.mogakco.domain.todo.application.service.todo;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.todo.dto.request.ToDoDTO;
import project.mogakco.domain.todo.dto.response.ToDoResponseDTO;
import project.mogakco.domain.todo.entity.ToDo;

import java.util.List;

public interface ToDoService {

	ToDoResponseDTO createOneToDoTap(ToDoDTO.ToDoCreateDTO toDoCreateDTO);

	ResponseEntity<?> writeContentsOneToDoTap(ToDoDTO.ToDoWriteContentsDTO toDoWriteContentsDTO);

	ResponseEntity<?> eliminateOneToDoTap(ToDoDTO.ToDoEliminateDTO toDoEliminateDTO);

	ToDoResponseDTO changeTitleTodo(ToDoDTO.ChangTitleDTO changTitleDTO);

	ResponseEntity<?> getTodoListInfoByCategorySeq(Long categorySeq);

	ResponseEntity<?> getTodoOneTapByTodoSeq(Long todoSeq);

	ResponseEntity<?> changeCategoryTodo(ToDoDTO.ChangCategoryDTO changCategoryDTO);
}
