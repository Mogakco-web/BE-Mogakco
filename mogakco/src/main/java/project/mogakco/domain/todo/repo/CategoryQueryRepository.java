package project.mogakco.domain.todo.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CategoryQueryRepository {
	private final JPAQueryFactory queryFactory;

	public void createCategoryByOauthIdAndName(){

	}
}
