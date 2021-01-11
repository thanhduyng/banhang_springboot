package learncode.spring.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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

import learncode.spring.comon.Xuly;
import learncode.spring.model.Bills;
import learncode.spring.repository.BillsRepository;
import learncode.spring.service.IBillsService;

@Controller
@RequestMapping("/khachhang")
public class KhachHangController {

	@Autowired
	IBillsService iBillsService;
	
	@Autowired
	BillsRepository billsRepository;
	
	@GetMapping("/search/{pageNumber}")
	public String search(@RequestParam("display_name") String display_name, Model model, HttpSession session,
			HttpServletRequest request, @PathVariable int pageNumber) {
		if (display_name.equals("")) {
			
			return "redirect:/khachhang/list";
		}
		List<Bills> list =  billsRepository.findByDisplayName(display_name);
		
		if (0 == list.size()) {
			session.setAttribute("messenger", "Không tìm thấy");
			return "redirect:/khachhang/list";
		} else {
			PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("khachhanglist");
			int pagesize = 3;
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);

			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
			request.getSession().setAttribute("khachhanglist", pages);
			int current = pages.getPage() + 1;
			int begin = Math.max(1, current - list.size());
			int end = Math.min(begin + 5, pages.getPageCount());
			int totalPageCount = pages.getPageCount();
			String baseUrl = "/khachhang/page/";
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("totalPageCount", totalPageCount);
			model.addAttribute("baseUrl", baseUrl);
			model.addAttribute("KhachHang", pages);
		}
		return "/admin/view-khachhang";
		
	}

	@RequestMapping("/list")
	@PreAuthorize("hasPermission('', 'dskh')")
	public String list(ModelMap model, HttpSession session, HttpServletRequest request) {
		model.addAttribute("KhachHang", iBillsService.findAll());
		request.getSession().setAttribute("khachhanglist", null);
		return "redirect:/khachhang/page/1";

	}

	@GetMapping("/page/{pageNumber}")
//	@PreAuthorize("hasPermission('', 'danhsachslides')")
	public String showEmployeePage(Model mode, HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("khachhanglist");
		int pagesize = 5;
		List<Bills> list = iBillsService.findAll();
		mode.addAttribute("listbills", iBillsService.findAll());
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
		request.getSession().setAttribute("khachhanglist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/khachhang/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("KhachHang", pages);

		return "/admin/view-khachhang";
	}

	@RequestMapping("/")
	public String view(ModelMap model) {
		Bills bills = new Bills();
		model.addAttribute("Bills", bills);
		model.addAttribute("ACTION", "/khachhang/update");
		return "/admin/register-khachhang";

	}

	@PostMapping("/save")
	@PreAuthorize("hasPermission('', 'themkh')")
	public String save(@Valid @ModelAttribute("Bills") Bills bills, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register-khachhang";
		} else {
			bills.setMatkhau(Xuly.giaiMd5(bills.getMatkhau()));
			iBillsService.save(bills);
			return "/admin/register-khachhang";
		}
	}

	@PostMapping("/update")
	@PreAuthorize("hasPermission('', 'suakh')")
//	@PreAuthorize("hasPermission('', 'suadm')")
	public String Update(@ModelAttribute("Bills") Bills bills) {
		/*
		 * if (bindingResult.hasErrors()) { return "register-khachhang2"; } else {
		 */
		bills.setMatkhau(Xuly.giaiMd5(bills.getMatkhau()));
		iBillsService.save(bills);
		return "redirect:/khachhang/list";
	}

	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Long id) {

		Optional<Bills> u = iBillsService.findById(id);
		if (u.isPresent()) {
			model.addAttribute("Bills", u.get());
			request.getSession().setAttribute("bill", null);
		} else {
			model.addAttribute("Bills", new Bills());
		}
		model.addAttribute("ACTION", "/khachhang/update");
		return "/admin/register-khachhang2";
	}

	@RequestMapping("/delete/{id}")
	@PreAuthorize("hasPermission('', 'xoakh')")
	public String delete(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Long id) {
		iBillsService.deleteById(id);
		request.getSession().setAttribute("bill", null);
		model.addAttribute("KhachHang", iBillsService.findAll());
		return "redirect:/khachhang/list";
	}
}
