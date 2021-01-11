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

import learncode.spring.model.Category;
import learncode.spring.repository.CategoryRepository;
import learncode.spring.service.CategorySevice;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategorySevice categorySevice;

	@GetMapping("/")
	@PreAuthorize("hasPermission('', 'themdm')")
	public String add(ModelMap model) {
		Category category = new Category();
		model.addAttribute("CATEGORY", category);
		model.addAttribute("ACTION", "/category/save");
		return "/admin/register-category";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("CATEGORY") Category category, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/admin/register-category";
		} else {
			categorySevice.save(category);
			return "/admin/register-category";
		}
	}

	@PostMapping("/update")
	@PreAuthorize("hasPermission('', 'suadm')")
	public String Update(@Valid @ModelAttribute("CATEGORY") Category category, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/admin/register-category";
		} else {
			categorySevice.save(category);
			return "/admin/register-category2";
		}
	}

	@RequestMapping("/list")
	@PreAuthorize("hasPermission('', 'danhsachdm')")
	public String list(ModelMap model) {
		model.addAttribute("LIST_CATEGORY", categorySevice.findAll());
		return "/admin/view-category";
	}

	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Integer id) {

		Optional<Category> u = categorySevice.findById(id);
		if (u.isPresent()) {
			model.addAttribute("CATEGORY", u.get());
			request.getSession().setAttribute("category", null);
		} else {
			model.addAttribute("CATEGORY", new Category());
		}
		model.addAttribute("ACTION", "/category/update");
		return "/admin/register-category2";
	}

	@RequestMapping("/delete/{id}")
	@PreAuthorize("hasPermission('', 'xoadm')")
	public String delete(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Integer id) {
		categorySevice.deleteById(id);
		request.getSession().setAttribute("category", null);
		model.addAttribute("LIST_CATEGORY", categorySevice.findAll());
//		return "view-category";
		return "redirect:/category/page/1";
	}

	@GetMapping("/page/{pageNumber}")
	public String showEmployeePage(Model mode, HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("category");
		int pagesize = 7;
		List<Category> list = (List<Category>) categorySevice.findAll();
		mode.addAttribute("listcategory", categoryRepository.findAll());
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
		request.getSession().setAttribute("category", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/category/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("LIST_CATEGORY", pages);

		return "/admin/view-category";
	}

	@GetMapping("/find")
	public String find(ModelMap model, @RequestParam("loaimon") String loaimon) {
		model.addAttribute("LIST_CATEGORY", categorySevice.findByLoaimonContaining(loaimon));
		return "/admin/view-category";
	}

	@GetMapping("/search/{pageNumber}")
	public String search(@RequestParam("loaimon") String loaimon, Model model, HttpSession session,
			HttpServletRequest request, @PathVariable int pageNumber) {
		if (loaimon.equals("")) {
			return "redirect:/category/page/1";
		}
		List<Category> list = categorySevice.findByLoaimonContaining(loaimon);
		if (0 == list.size()) {
			session.setAttribute("messenger", "Không tìm thấy");
		} else {
			PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("categorylist");
			int pagesize = 3;
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);

			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
			request.getSession().setAttribute("categorylist", pages);
			int current = pages.getPage() + 1;
			int begin = Math.max(1, current - list.size());
			int end = Math.min(begin + 5, pages.getPageCount());
			int totalPageCount = pages.getPageCount();
			String baseUrl = "/category/page/";
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("totalPageCount", totalPageCount);
			model.addAttribute("baseUrl", baseUrl);
			model.addAttribute("LIST_CATEGORY", pages);

		}
		return "/admin/view-category";
	}
}