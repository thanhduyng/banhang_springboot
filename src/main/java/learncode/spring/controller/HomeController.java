package learncode.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import learncode.spring.comon.Xuly;
import learncode.spring.model.BillDetail;
import learncode.spring.model.Bills;
import learncode.spring.model.TinTuc;
import learncode.spring.repository.BillsRepository;
import learncode.spring.service.BillDetailService;
import learncode.spring.service.CategoryServiceImpl;
import learncode.spring.service.IBillsService;
import learncode.spring.service.IProductService;
import learncode.spring.service.TinTucService;

@Controller
@RequestMapping("/come")
public class HomeController extends BaseController {

	@Autowired
	TinTucService tinTucService;

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private IProductService _productService;

	@Autowired
	IBillsService iBillsService;

	@Autowired
	BillsRepository billsRepository;
	
	@Autowired
	BillDetailService billDetailService;
	
	@GetMapping("/lichsuhoadon/dang-xu-li")
	public String Lichsuhoadon(HttpSession session, ModelMap model) {
		Bills email = (Bills) session.getAttribute("CUSTOMER");
		List<BillDetail> list = (List<BillDetail>) this.billDetailService.getLichsudonhang(email);
		if (list == null) {
			return "redirect:/come/";
		}

		model.addAttribute("LICHSUDONHANG", list);
		return "/user/lichsudonhang";
	}
	
	@GetMapping("/lichsuhoadon/dang-giao")
	public String danggiao(HttpSession session, ModelMap model) {
		Bills email = (Bills) session.getAttribute("CUSTOMER");
		List<BillDetail> list = (List<BillDetail>) this.billDetailService.getLichsudonhang2(email);
		if (list == null) {
			return "redirect:/come/";
		}

		model.addAttribute("LICHSUDONHANG", list);
		return "/user/lichsudonhang";
	}
	
	@GetMapping("/lichsuhoadon/da-giao")
	public String dagiao(HttpSession session, ModelMap model) {
		Bills email = (Bills) session.getAttribute("CUSTOMER");
		List<BillDetail> list = (List<BillDetail>) this.billDetailService.getLichsudonhang3(email);
		if (list == null) {
			return "redirect:/come/";
		}

		model.addAttribute("LICHSUDONHANG", list);
		return "/user/lichsudonhang";
	}
	
	@RequestMapping("/ho-so")
	public String viewthongtin() {
		return "/user/ho-so-ca-nhan";
	}
	

	@PostMapping(value = "/capnhap-thongtin")
	public String capnhap(Bills b) {
		b.setMatkhau(Xuly.giaiMd5(b.getMatkhau()));
		this.iBillsService.capnhapBill(b);
		return "redirect:/come/ho-so/";
	}

	@RequestMapping("/")
	public ModelAndView Index() {
//		_mvShare.addObject("menus",  _homeService.GetDataMenus());
		_mvShare.addObject("slides", _homeService.GetDataSlide());
		_mvShare.addObject("categorys", _homeService.GetDataCategory());
		_mvShare.addObject("new_products", _homeService.NewDataProducts());
		_mvShare.addObject("highlight", _homeService.HighLightDataProducts());
		_mvShare.addObject("sale", _homeService.GetSale());
		_mvShare.addObject("TINTUC", tinTucService.getAllTinTuc());
		_mvShare.setViewName("/layouts/user");
		return _mvShare;
	}

	@RequestMapping(value = "/san-pham/{id}")
	public ModelAndView Product(@PathVariable String id) {
//		_mvShare.setViewName("redirect:/page/1");
		_mvShare.setViewName("/user/ds-sanpham");
		// lấy id_category
		_mvShare.addObject("idCategory", id);
		// lấy products theo id_category
		_mvShare.addObject("products", categoryService.GetAllProductsByID(Integer.parseInt(id)));
		// all category
		_mvShare.addObject("categorys", _homeService.GetDataCategory());
		return _mvShare;
	}

	@RequestMapping("/thongtinkhachhang")
	public ModelAndView thongtin(ModelMap model) {
		_mvShare.setViewName("/user/thong-tin-khach-hang");
		return _mvShare;
	}

	@RequestMapping("/gioi-thieu")
	public String gioithieu() {
		_mvShare.addObject("categorys", _homeService.GetDataCategory());
		return "/user/view-gioithieu";
	}

	@RequestMapping("/chi-tiet-san-pham/{id}")
	public ModelAndView products(@PathVariable int id) {
		_mvShare.setViewName("/user/sanpham-details");
		_mvShare.addObject("product", _productService.GetProductByID(id));
		int idCategory = _productService.GetProductByID(id).getId_category();
		_mvShare.addObject("productByIDCategory", _productService.GetProductByIDCategory(idCategory));
		return _mvShare;
	}

	@RequestMapping("/tin-tuc/chi-tiet-tin-tuc/{id}")
	public String TinTuc(@PathVariable int id, ModelMap model) {
		model.addAttribute("chitiettintuc", tinTucService.GetTinTucByID(id));
		model.addAttribute("tintuc", tinTucService.getAllTinTuc());	
		return "/user/tintuc-details";
	}


	@GetMapping("/tin-tuc/trang/{pageNumber}")
//	@PreAuthorize("hasPermission('', 'danhsachslides')")
	public String showPage(Model mode, HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("tintuclist");
		int pagesize = 5;
		List<TinTuc> list = tinTucService.findAll();
		model.addAttribute("categorys", _homeService.GetDataCategory());
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
		request.getSession().setAttribute("tintuclist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/come/tin-tuc/trang/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("TINTUC", pages);

		return "/user/ds-tintuc";
	}

}
