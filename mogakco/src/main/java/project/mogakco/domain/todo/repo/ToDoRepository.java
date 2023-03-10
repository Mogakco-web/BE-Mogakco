package project.mogakco.domain.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.entity.ToDo;

import java.util.Optional;

public interface ToDoRepository extends JpaRepository<ToDo,Long> {
	Optional<ToDo> findByTodoSeqAndMemberSocial(Long todoSeq, MemberSocial memberSocial);
}
