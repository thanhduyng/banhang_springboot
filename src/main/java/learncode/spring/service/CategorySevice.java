package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import learncode.spring.model.Category;


public interface CategorySevice {

	void deleteAll();

	void deleteAll(List<Category> entities);

	void delete(Category entity);

	void deleteById(Integer id);

	long count();

	List<Category> findAllById(List<Integer> ids);

	List<Category> findAll();

	boolean existsById(Integer id);

	Optional<Category> findById(Integer id);

	List<Category> saveAll(List<Category> entities);

	Category save(Category entity);
	
	List<Category> findByloaimonStartingWith(String loaimon);
	
	List<Category> findByLoaimonContaining(String loaimon);

}
