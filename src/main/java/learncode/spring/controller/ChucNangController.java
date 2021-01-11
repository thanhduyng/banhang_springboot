package learncode.spring.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import learncode.spring.comon.Xuly;
import learncode.spring.model.ChucNang;
import learncode.spring.service.ChucNangService;
import learncode.spring.service.NguoiDungService;

@Controller
@RequestMapping("/chucnang")
public class ChucNangController {

	@Autowired
	ChucNangService chucNangService;

	@Autowired
	NguoiDungService nguoiDungService;

	@RequestMapping(value = "/", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('', 'themcn')")
	public String addOrEdit(ModelMap model) {
		ChucNang cn = new ChucNang();
		model.addAttribute("CHUCNANG", cn);
		return "/admin/register-chucnang1";
	}

	@RequestMapping(value = "/saveChucNang", method = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('', 'themcn')")
	public String doSave(ModelMap model, @Valid @ModelAttribute("CHUCNANG") ChucNang cn, BindingResult bindingResult,
			Principal principal) {
		if (bindingResult.hasErrors()) {
			return "/admin/register-chucnang1";
		} else {
			cn.setId(ThreadLocalRandom.current().nextLong(0, new Long("9000000000000000")));
			cn.setCreateday(new Timestamp(new Date().getTime()));
			cn.setUpdateday(new Timestamp(new Date().getTime()));
			cn.setNguoitao(principal.getName());
			cn.setNguoiupdate(principal.getName());
			this.chucNangService.insertChucNang(cn);
			return "redirect:/chucnang/list";
		}
	}

	@GetMapping("/chucnang-update")
	@ResponseBody
	@PreAuthorize("hasPermission('', 'suacn')")
	public Optional<ChucNang> findByChucNangEditId(ModelMap model, Long id) {
		return this.chucNangService.findByChucNangEditId(id);
	}

//	@PostMapping("/update")
//	public String Update(@ModelAttribute("CHUCNANG") ChucNang cn) {
//		chucNangService.updateChucNang(cn);
//		return "register-chucnang";
//	}

	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Long id) {

		Optional<ChucNang> u = chucNangService.findByChucNangEditId(id);
		if (u.isPresent()) {
			model.addAttribute("CHUCNANG", u.get());
			request.getSession().setAttribute("chucnang", null);
		} else {
			model.addAttribute("CHUCNANG", new ChucNang());
		}
		model.addAttribute("ACTION", "/chucnang/updateChucNang");
		return "/admin/register-chucnang2";
	}

	@RequestMapping("/delete/{id}")
	public String delete(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Long id) {
		chucNangService.deleteById(id);
		request.getSession().setAttribute("chucnang", null);
		model.addAttribute("CHUCNANGS", chucNangService.findAllChucNang());
		return "redirect:/chucnang/list/";
	}

	@GetMapping("/chucnang-chitiet")
	@ResponseBody
	@PreAuthorize("hasPermission('', 'xctcn')")
	public Optional<ChucNang> findByChitietChucnang(ModelMap model, Long id) {
		return this.chucNangService.findByChucNangEditId(id);
	}

	@RequestMapping("/updateChucNang")
	public String doUpdate(ChucNang cn, Principal principal) {
		cn.setUpdateday(new Timestamp(new Date().getTime()));
		cn.setNguoiupdate(principal.getName());
		this.chucNangService.updateChucNang(cn);
		return "redirect:/chucnang/list";
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ModelMap model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("chucnanglist", null);
		return "redirect:/chucnang/list/page/1";
	}

	@RequestMapping(value = "/list/page/{pageNumber}", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('', 'danhsachcn')")
	public String showChucNangsPage(HttpServletRequest request, @PathVariable int pageNumber, ModelMap model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("chucnanglist");
		int pagesize = 8;
		List<ChucNang> list = (List<ChucNang>) this.chucNangService.getAllChucNang();
		int sum = list.size();
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("chucnanglist", pages);

		int current = pages.getPage() + 1;
		System.out.println(current);
		int begin = Math.max(1, current - list.size());

		int end = Math.min(begin + 5, pages.getPageCount());

		int totalPageCount = pages.getPageCount();

		String baseUrl = "/list/page/";

		model.addAttribute("sum", sum);
		model.addAttribute("beginIndex", begin);

		model.addAttribute("endIndex", end);

		model.addAttribute("currentIndex", current);

		model.addAttribute("totalPageCount", totalPageCount);

		model.addAttribute("baseUrl", baseUrl);

		model.addAttribute("CHUCNANGS", pages);

		return "/admin/view-chucnang";
	}

	@RequestMapping("/key")
	public List<String> key(ModelMap model) {
		return this.chucNangService.maapi();
	}

	@RequestMapping("/dataSearch")
	public String dateSearch(@RequestParam("keyword") String keywork, HttpSession session) {
		session.setAttribute("KEYWORK", keywork);

		if (keywork == null || keywork.equals("")) {
			return "redirect:/chucnang/list";
		} else {
			keywork = Xuly.xuLySearch(keywork);
			session.setAttribute("KEYWORK", keywork);
			return "redirect:/chucnang/list/search/1";
		}
	}

	@RequestMapping("/list/search/{pageNumber}")
	public String search(ModelMap model, HttpServletRequest request, @PathVariable int pageNumber,
			HttpSession session) {
		String tenchucnang = (String) session.getAttribute("KEYWORK");
		List<ChucNang> list = this.chucNangService.findByTenchucnang(tenchucnang);
		if (list == null) {
			return "redirect:/chucnang/list/";
		}
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("chucnanglist");
		int pagesize = 8;
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);
		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("chucnanglist", pages);

		int current = pages.getPage() + 1;

		int begin = Math.max(1, current - list.size());

		int end = Math.min(begin + 5, pages.getPageCount());

		int totalPageCount = pages.getPageCount();

		String baseUrl = "/list/page/";

		model.addAttribute("beginIndex", begin);

		model.addAttribute("endIndex", end);

		model.addAttribute("currentIndex", current);

		model.addAttribute("totalPageCount", totalPageCount);

		model.addAttribute("baseUrl", baseUrl);

		model.addAttribute("CHUCNANGS", pages);

		return "/admin/view-chucnang";
	}

	@RequestMapping("/del")
	@PreAuthorize("hasPermission('', 'xoacn')")
	public String delete(ModelMap model, @RequestParam("lcn") List<Long> id, Principal principal) {
		for (Long lg : id) {
			ChucNang ChucNang = this.chucNangService.findById(lg).get();
			if (ChucNang.getParentid() < 0) {
				ChucNang.setIsdelete((Integer) 1);
				ChucNang.setNguoiupdate(principal.getName());
				this.chucNangService.updateChucNang(ChucNang);
			}
		}

		for (Long long1 : id) {
			ChucNang ChucNang = this.chucNangService.findById(long1).get();
			if (ChucNang.getParentid() > 0 || 0 == this.chucNangService.count(ChucNang.getId())) {
				ChucNang.setIsdelete((Integer) 1);
				ChucNang.setNguoiupdate(principal.getName());
				this.chucNangService.updateChucNang(ChucNang);
			}
		}
		return "redirect:/chucnang/list/";
	}

	@ModelAttribute("PARENTID")
	public List<ChucNang> getParent() {
		return this.chucNangService.getAllChucNangParent();
	}

}
