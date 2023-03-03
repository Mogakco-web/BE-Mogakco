package project.mogakco.domain.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.todo.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	Optional<Category> findByCategory_name(String category_name);
}
