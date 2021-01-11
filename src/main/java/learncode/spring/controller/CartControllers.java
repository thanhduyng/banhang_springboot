package learncode.spring.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import learncode.spring.comon.Xuly;
import learncode.spring.dto.CartDto;
import learncode.spring.model.BillDetail;
import learncode.spring.model.Bills;
import learncode.spring.model.Products;
import learncode.spring.repository.BillsDao;
import learncode.spring.repository.BillsRepository;
import learncode.spring.repository.CartDao;
import learncode.spring.repository.ProductRepository;
import learncode.spring.service.BillDetailService;
import learncode.spring.service.CartServiceImpl;
import learncode.spring.service.IBillsService;
import learncode.spring.service.ProductService;

@Controller
public class CartControllers extends BaseController {

	@Autowired
	private CartServiceImpl cartService = new CartServiceImpl();

	@Autowired
	ProductRepository productRepository;

	@Autowired
	BillsDao billsDao;

	@Autowired
	CartDao cartDao;

	@Autowired
	ProductService productService;

	@Autowired
	IBillsService ibillservice;

	@Autowired
	BillsRepository billRepository;

	@Autowired
	BillDetailService billDetailService;

	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Long id) {

		Optional<Bills> u = ibillservice.findById(id);
		if (u.isPresent()) {
			model.addAttribute("Bills", u.get());
			request.getSession().setAttribute("bill", null);
		} else {
			model.addAttribute("Bills", new Bills());
		}
		model.addAttribute("ACTION", "/khachhang/update");
		return "/user/register-khachhhang2";
	}

	@GetMapping("/dang-nhap")
	public String login(ModelMap model) {
		model.addAttribute("categorys", _homeService.GetDataCategory());
		Bills bills = new Bills();
		model.addAttribute("BILL", bills);
		model.addAttribute("ACTION", "/dang-ki");
		return "/user/dang-nhap";
	}

	@PostMapping("/dang-ki")
	public String Dangki(HttpServletRequest request, ModelMap model, @Valid @ModelAttribute("BILL") Bills bills,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/user/dang-nhap";
		} else {
			bills.setMatkhau(Xuly.giaiMd5(bills.getMatkhau()));
			bills.setTrangthai((Integer) 1);
			ibillservice.save(bills);
			model.addAttribute("success", "Đăng kí thành công");

//		return "redirect:" + request.getHeader("Referer");
			return "/user/dang-nhap";
		}
	}

	@GetMapping("/dang-xuat")
	public String logout(HttpSession session, HttpServletRequest request) {
		session.removeAttribute("Cart");
		session.removeAttribute("CUSTOMER");
//		return "redirect:" + request.getHeader("Referer");
		return "redirect:/dang-nhap";
	}

	@PostMapping(value = "/kiemtra-dangnhap")
	public String checkLogin(ModelMap model, HttpSession session, @RequestParam("email") String email,
			@RequestParam("matkhau") String matkhau) {

		if (this.ibillservice.checkLogin(email, matkhau)) {
			session.setAttribute("CUSTOMER", this.billRepository.findByEmail(email));
			System.out.println("đăng nhập thành công");
			return "redirect:/gio-hang";
		} else {
			System.out.println("đăng nhập thất bại");

		}
		return "redirect:/dang-nhap";
	}

	@RequestMapping(value = "gio-hang")
	public ModelAndView Index() {
		_mvShare.addObject("categorys", _homeService.GetDataCategory());
		_mvShare.setViewName("/user/view-cart");
		return _mvShare;
	}

	@RequestMapping(value = "AddCart/{id}")
	public String AddCart(HttpServletRequest request, HttpSession session, @PathVariable long id) {
		HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("Cart");
		if (cart == null) {
			cart = new HashMap<Long, CartDto>();
		}
		cart = cartService.AddCart(id, cart);

		session.setAttribute("Cart", cart);
		// model.addAttribute("Cart", cart);
		session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
		session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));
		return "redirect:" + request.getHeader("Referer");
	}

	@RequestMapping("/update")
	public String update(HttpSession session, @RequestParam("id") Long id, @RequestParam("qty") int quanty,
			HttpServletRequest request) {
		Optional<Products> carts = productService.findById(id);
		HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("Cart");
		if (cart == null) {
			cart = new HashMap<Long, CartDto>();
		}
		cart = cartService.EditCart(id, quanty, cart);
		session.setAttribute("Cart", cart);
		session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
		session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));
		return "redirect:" + request.getHeader("Referer");
	}

	@RequestMapping(value = "DeleteCart/{id}")
	public String DeleteCart(HttpServletRequest request, HttpSession session, @PathVariable long id) {
		HashMap<Long, CartDto> cart = (HashMap<Long, CartDto>) session.getAttribute("Cart");
		if (cart == null) {
			cart = new HashMap<Long, CartDto>();
		}
		cart = cartService.DeleteCart(id, cart);
		session.setAttribute("Cart", cart);
		session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
		session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));
		return "redirect:" + request.getHeader("Referer");
	}

	// kiểm tra xem có tài khoản hay chưa
	@GetMapping(value = "/checkout")
	public String viewCheckout(ModelMap model, HttpSession session) {
		if (session.getAttribute("CUSTOMER") == null && session.getAttribute("INFO") == null) {
			Bills bills = new Bills();
			model.addAttribute("ACTION", "/savecheckout");
			model.addAttribute("bills", bills);
			return "redirect:/dang-nhap";
		}
		return "/user/thanhtoan";
	}

	// thêm sản phẩm vào database
	@PostMapping("/thanhtoan")
	public String chekoutPay(HttpSession session, @RequestParam("id") Bills id) {
		HashMap<Long, CartDto> cartItems = (HashMap<Long, CartDto>) session.getAttribute("Cart");
		if (cartItems == null) {
			cartItems = new HashMap<>();
		}
//		Bills bill = new Bills();
//		bill.setQuantity((int) session.getAttribute("TotalQuantyCart"));
//		bill.setTotal((double) session.getAttribute("TotalPriceCart"));
//		bill.setTrangthai((Integer) 1);
		for (Map.Entry<Long, CartDto> itemCart : cartItems.entrySet()) {
			BillDetail billDetail = new BillDetail();
			billDetail.setBills(id);
			billDetail.setProducts(productRepository.findById(itemCart.getValue().getProduct().getId_product()).get());
			billDetail.setQuantity(itemCart.getValue().getQuanty());

			billDetail.setTotal(itemCart.getValue().getQuanty() * (itemCart.getValue().getProduct().getPrice() 
			- itemCart.getValue().getProduct().getGiamgia()));

			billDetail.setTrangthai((Integer) 1);
			billDetail.setDatetime(LocalDateTime.now());
			billsDao.AddBillsDetail(billDetail);

		}
		session.removeAttribute("Cart");
		session.removeAttribute("TotalQuantyCart");
		session.removeAttribute("TotalPriceCart");
		/* cartItems = new HashMap<>(); */
		return "redirect:/da-thanh-toan";
	}

	@RequestMapping("/da-thanh-toan")
	public String tt(HttpSession session) {
		return "/user/da-thanh-toan";
	}

}
