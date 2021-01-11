package learncode.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.dto.ProductsDto;
import learncode.spring.repository.ProductsDao;

@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	ProductsDao productDao = new ProductsDao();

	public ProductsDto GetProductByID(int id) {
		List<ProductsDto> listProducts = productDao.GetProductByID(id);
		return listProducts.get(0);
	}

	public List<ProductsDto> GetProductByIDCategory(int id) {
		return productDao.GetAllProductsByID(id);
	}

	


	
}
