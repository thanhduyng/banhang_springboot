package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.model.Category;
import learncode.spring.model.Products;
import learncode.spring.repository.CategoryRepository;
import learncode.spring.repository.ProductRepository;

@Service
public class ProductServiceIml implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Products save(Products entity) {
		return productRepository.save(entity);
	}

	@Override
	public List<Products> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Products> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Products getOne(Long id) {
		return productRepository.getOne(id);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override
	public List<Category> findAllCategory(){
		return (List<Category>)categoryRepository.findAll();
	}

	@Override
	public List<Products> findByNameContaining(String name) {
		return productRepository.findByNameContaining(name);
	}

	@Override
	public void changeProductPrice(Long id, Double price) {
		Products p = new Products();
    	p = productRepository.findById(id).get();
    	p.setPrice(price);
    	productRepository.save(p);
		
	}

	@Override
	public List<Products> findAllByNameAndDM(String namesp, List<Long> categoryid) {
		return productRepository.findAllByNameAndDM(namesp, categoryid);
	}

	@Override
	public List<Products> findAllByName(String namesp) {
		return productRepository.findAllByName(namesp);
	}

	@Override
	public List<Products> findAllByDM(List<Long> categoryid) {
		return productRepository.findAllByDM(categoryid);
	}


}
