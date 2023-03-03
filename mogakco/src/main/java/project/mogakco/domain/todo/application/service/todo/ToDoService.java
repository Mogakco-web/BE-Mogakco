package project.mogakco.domain.todo.application.service.todo;

import project.mogakco.domain.todo.dto.request.ToDoDTO;
import project.mogakco.domain.todo.entity.ToDo;

public interface ToDoService {

	ToDo createOneToDoTap(ToDoDTO.ToDoCreateDTO toDoCreateDTO, String oauthId);


}
