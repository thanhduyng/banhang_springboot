package learncode.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.dto.ProductsDto;
import learncode.spring.repository.ProductsDao;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ProductsDao productsDao;

	public List<ProductsDto> GetAllProductsByID(int id) {
		return productsDao.GetAllProductsByID(id);
	}

	public List<ProductsDto> GetAllProducts() {
		return productsDao.GetAllProducts();
	}

}
