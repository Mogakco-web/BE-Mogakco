package project.mogakco.domain.todo.application.impl.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mogakco.domain.todo.application.service.category.CategoryService;
import project.mogakco.domain.todo.entity.Category;
import project.mogakco.domain.todo.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public Category createCategoryOne(String category_name) {
		return categoryRepository.save(
				Category.builder()
						.category_name(category_name)
						.build()
		);

	}

	@Override
	public List<Category> getListOfCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryInfoName(String category_name) {
		return categoryRepository.findByCategory_name(category_name).orElse(null);
	}
}
