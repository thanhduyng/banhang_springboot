package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import learncode.spring.model.Category;
import learncode.spring.model.Products;


@Service
@Transactional
public interface ProductService  {
	
	void deleteAll();

	void deleteById(Long id);

	long count();

	Products getOne(Long id);

	Optional<Products> findById(Long id);

	List<Products> findAll();

	Products save(Products entity);	
	
	List<Category> findAllCategory();
	
	List<Products> findByNameContaining(String name);

	void changeProductPrice(Long id, Double price);

	List<Products> findAllByNameAndDM(String namesp, List<Long> categoryid);

	List<Products> findAllByName(String namesp);

	List<Products> findAllByDM(List<Long> categoryid);

	
}
