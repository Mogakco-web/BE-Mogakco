package project.mogakco.domain.todo.application.service.category;

import org.springframework.http.ResponseEntity;
import project.mogakco.domain.todo.entity.Category;

import java.util.List;

public interface CategoryService {

	Category createCategoryOne(String category_name);

	List<Category> getListOfCategory();

	Category getCategoryInfoName(String category_name);
}
