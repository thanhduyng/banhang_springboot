package learncode.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import learncode.spring.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query
	List<Category> findByloaimonStartingWith(String loaimon);

	List<Category> findByLoaimonContaining(String loaimon);
}
