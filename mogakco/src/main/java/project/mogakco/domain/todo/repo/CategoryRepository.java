package project.mogakco.domain.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mogakco.domain.member.entity.member.MemberSocial;
import project.mogakco.domain.todo.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	Optional<Category> findByCategoryName(String categoryName);
	Optional<Category> findByCategoryNameAndMemberSocial(String category_name, MemberSocial memberSocial);
	Optional<Category> findByCategorySeqAndMemberSocial(Long categorySeq,MemberSocial memberSocial);
}
