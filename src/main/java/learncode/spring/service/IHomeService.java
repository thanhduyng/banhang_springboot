package learncode.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.model.Slides;

@Service
public interface IHomeService {

	@Autowired
//	public List<Menus> GetDataMenus();
	public List<Slides> GetDataSlide();
//	public List<ProductsDto> GetDataProducts();
}
