package learncode.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import learncode.spring.comon.Xuly;
import learncode.spring.dto.ProductDTO;
import learncode.spring.model.Category;
import learncode.spring.model.Products;
import learncode.spring.repository.ProductRepository;
import learncode.spring.service.CategorySevice;
import learncode.spring.service.ProductService;

@Controller
@RequestMapping("/sanpham")
public class ProductController extends BaseController {

	@Autowired
	ProductService service;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategorySevice categorySevice;

	@PostMapping("/changePrice")
	public String changePrice(@RequestParam("id") Long id, @RequestParam("newPrice") Double price,
			HttpServletRequest request) {
		service.changeProductPrice(id, price);
		request.getSession().setAttribute("cartlist", null);
		return "redirect:/sanpham/page/1";
	}


	@GetMapping("/page/{pageNumber}")
	@PreAuthorize("hasPermission('', 'danhsachsp')")
	public String showEmployeePage(Model mode, HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("cartlist");
		int pagesize = 7;
		List<Products> list = (List<Products>) service.findAll();
		mode.addAttribute("listproducts", productRepository.findAll());
		System.out.println(list.size());

		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("cartlist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/sanpham/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("products", pages);

		return "/admin/view-product";
	}

	@GetMapping("/")
//	@PreAuthorize("hasPermission('', 'themsp')")
	public String addOrEdit(ModelMap model) {
		ProductDTO productDTO = new ProductDTO();
		model.addAttribute("PRODUCTDTO", productDTO);
		model.addAttribute("ACTION", "/sanpham/saveOrUpdate");
		return "/admin/new-product";
	}

	@RequestMapping("/edit/{id}")
	@PreAuthorize("hasPermission('', 'suasp')")
	public String edit(ModelMap model, HttpServletRequest request, @PathVariable(name = "id") Long id) {
		Optional<Products> opProduct = service.findById(id);
		ProductDTO dto = null;
		if (opProduct.isPresent()) {
			Products pt = opProduct.get();
			File file = new File("uploads/" + pt.getImage());
			FileInputStream input;
			try {
				input = new FileInputStream(file);
				MultipartFile mutiPhoto = new MockMultipartFile("file", file.getName(), "text/plain",
						IOUtils.toByteArray(input));

				dto = new ProductDTO(pt.getId(), pt.getName(), mutiPhoto, pt.getPrice(), pt.getGiamgia(), pt.getCategorys().getId(), pt.getSale(), pt.isHighlight(),
						pt.isNew_product(), pt.getDetails(), pt.getCreated_at(), pt.getUpdated_at());
//					dto = new ProductDTO(pt.getId(), pt.getName(), mutiPhoto, pt.getSku(), pt.getPrice(), pt.getCategorys().getId());

			} catch (FileNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			model.addAttribute("PRODUCTDTO", dto);
			request.getSession().setAttribute("cartlist", null);
		} else {
			model.addAttribute("PRODUCTDTO", new ProductDTO());
		}
		model.addAttribute("ACTION", "/sanpham/saveOrUpdate");
		return "/admin/update-product";
	}

	@PostMapping("/saveOrUpdate")
//	@PreAuthorize("hasPermission('', 'themsp')")
	public String save(HttpServletRequest request, ModelMap model, @Valid @ModelAttribute("PRODUCTDTO") ProductDTO dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new-product";
		}
		Optional<Products> optionalProduct = service.findById(dto.getId());
		Products products = null;
		String image = "Logo.png";
		Path path = Paths.get("uploads/");
		if (optionalProduct.isPresent()) {
			// save

			if (dto.getImage().isEmpty()) {
				image = optionalProduct.get().getImage();

			} else {
				try {
					InputStream inputStream = dto.getImage().getInputStream();
					Files.copy(inputStream, path.resolve(dto.getImage().getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					image = dto.getImage().getOriginalFilename().toString();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		} else {
			// add new
			if (!dto.getImage().isEmpty()) {
				try {
					InputStream inputStream = dto.getImage().getInputStream();
					Files.copy(inputStream, path.resolve(dto.getImage().getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					image = dto.getImage().getOriginalFilename().toString();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		}

//		products = new Product(dto.getId(), dto.getName(), image, dto.getSku(), dto.getPrice(), new Category(dto.getId_category(),null, ""));
		products = new Products(dto.getId(), dto.getName(), dto.getPrice(), image, new Category(dto.getId_category(), image), dto.getSale(), dto.isHighlight(),
				dto.isNew_product(), dto.getDetails(), dto.getGiamgia(), dto.getCreated_at(), dto.getUpdated_at());
		service.save(products);
		request.getSession().setAttribute("cartlist", null);
		return "/admin/new-product";
	}

	@GetMapping("/list")
	public String list(ModelMap model, HttpServletRequest request) {
		request.getSession().setAttribute("cartlist", null);
		return "redirect:/sanpham/page/1";
	}

	@RequestMapping("/dataSearch")
	public String dataSearch(@RequestParam("namesp") String namesp,
			@RequestParam(name = "categorySearch[]", required = false) List<Long> categoryid, HttpSession session) {
		session.setAttribute("NAMESP", namesp);
		session.setAttribute("CATE", categoryid);
		if (null == namesp || namesp.equals("")) {
			if (null == categoryid) {
				return "redirect:/sanpham/list";
			} else {
				session.setAttribute("SEARCH", 3);
				return "redirect:/sanpham/search/1";
			}
		} else {
			if (categoryid != null) {
				namesp = Xuly.xuLySearch(namesp);
				session.setAttribute("KEYWORD", namesp);
				session.setAttribute("SEARCH", 1);
				return "redirect:/sanpham/search/1";
			} else {
				namesp = Xuly.xuLySearch(namesp);
				session.setAttribute("KEYWORD", namesp);
				session.setAttribute("SEARCH", 2);
				return "redirect:/sanpham/search/1";
			}
		}
	}

	@GetMapping("/search/{pageNumber}")
	public String search( Model model, HttpSession session, HttpServletRequest request, @PathVariable int pageNumber) {
		String namesp = (String) session.getAttribute("KEYWORD");
		@SuppressWarnings("unchecked")
		List<Long> categoryid = (List<Long>) session.getAttribute("CATE");
		Integer temp = (Integer) session.getAttribute("SEARCH");	
		List<Products> list = null;
		switch (temp) {
		case 1:
			list = this.service.findAllByNameAndDM(namesp ,categoryid);
			break;
		case 2:
			list = this.service.findAllByName(namesp);
			break;
		case 3:
			list = this.service.findAllByDM(categoryid);
			break;
		default:
			break;
		}
		
		if (list == null) {
			return "redirect:/product/page/1";
		}
			PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("cartlist");
			int pagesize = 3;
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);

			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
			request.getSession().setAttribute("cartlist", pages);
			int current = pages.getPage() + 1;
			int begin = Math.max(1, current - list.size());
			int end = Math.min(begin + 5, pages.getPageCount());
			int totalPageCount = pages.getPageCount();
			String baseUrl = "/sanpham/page/";
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("totalPageCount", totalPageCount);
			model.addAttribute("baseUrl", baseUrl);
			model.addAttribute("products", pages);
			return"/admin/view-product";
		}


	@ModelAttribute(name = "CATEGORY")
	public List<Category> getAllCategory() {
		return service.findAllCategory();
	}

	@RequestMapping("/delete/{id}")
	@PreAuthorize("hasPermission('', 'xoasp')")
	public String deleteProduct(HttpServletRequest request, @PathVariable(name = "id") long id) {
		service.deleteById(id);
		request.getSession().setAttribute("cartlist", null);
		return "redirect:/sanpham/page/{id}";
	}


}
