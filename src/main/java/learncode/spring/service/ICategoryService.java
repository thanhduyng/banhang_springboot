package learncode.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import learncode.spring.dto.ProductsDto;

@Service
public interface ICategoryService {
	
	public List<ProductsDto> GetAllProductsByID(int id);
	
}
