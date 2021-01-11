package learncode.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.dto.ProductsDto;
import learncode.spring.model.Bills;
import learncode.spring.model.Category;
import learncode.spring.model.Slides;
import learncode.spring.repository.BillsRepository;
import learncode.spring.repository.CategoryDao;
import learncode.spring.repository.ProductsDao;
import learncode.spring.repository.SlidesDao;

@Service
public class HomeServiceImpl {

//	@Autowired
//	MenuDao menuRepository;

	@Autowired
	private SlidesDao slidesRepository;

	@Autowired
	private CategoryDao categoryRepository;

	@Autowired
	private BillsRepository billsRepository;

	@Autowired
	private ProductsDao productsDao;

//	public List<Menus> GetDataMenus() {
//		return menuRepository.GetDataMenus();
//	}

	public List<Slides> GetDataSlide() {
		return slidesRepository.GetDataSlide();
	}

	public List<Category> GetDataCategory() {
		return categoryRepository.GetDataCategorys();
	}

	public List<Bills> GetDataBilldetails(long id) {
		return billsRepository.findAllBillID(id);
	}

	public List<ProductsDto> HighLightDataProducts() {
		List<ProductsDto> listProducts = productsDao.HighLightDataProducts();
		return listProducts;
	}

	public List<ProductsDto> NewDataProducts() {
		List<ProductsDto> listProducts = productsDao.NewDataProducts();
		return listProducts;
	}
	
	public List<ProductsDto> GetSale() {
		List<ProductsDto> listProducts = productsDao.GetSale();
		return listProducts;
	}
}
