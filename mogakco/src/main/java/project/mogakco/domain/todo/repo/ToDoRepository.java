package project.mogakco.domain.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.todo.entity.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo,Long> {
}
