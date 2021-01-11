package learncode.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import learncode.spring.dto.ProductsDto;

@Service
public interface IProductService {

	public ProductsDto GetProductByID(int id);

	public List<ProductsDto> GetProductByIDCategory(int id);

}
