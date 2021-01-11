package learncode.spring.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;

import learncode.spring.comon.BillDetailPDFExporter;
import learncode.spring.model.BillDetail;
import learncode.spring.model.Bills;
import learncode.spring.model.Products;
import learncode.spring.service.BillDetailService;

@Controller
@RequestMapping("/don-hang")
public class DonHangController {

	@Autowired
	BillDetailService billDetailService;

	@GetMapping("/users/export/pdf")
		@PreAuthorize("hasPermission('', 'pdf')")
	    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<BillDetail> listUsers = billDetailService.listAll();
	         
	        BillDetailPDFExporter exporter = new BillDetailPDFExporter(listUsers);
	        exporter.export(response);
	         
	    }

	@RequestMapping("/list")
	@PreAuthorize("hasPermission('', 'dsdh')")
	public String donhang(Model model) {
		model.addAttribute("LIST_DONHANG", billDetailService.findAll());
		return "redirect:/don-hang/page/1";
	}

	@GetMapping("/page/{pageNumber}")
//	@PreAuthorize("hasPermission('', 'danhsachslides')")
	public String showEmployeePage(Model mode, HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("billdetails");
		int pagesize = 5;
		List<BillDetail> list = billDetailService.findAll();
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
		request.getSession().setAttribute("billdetails", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/don-hang/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("LIST_DONHANG", pages);

		return "/admin/view-donhang";
	}

	@GetMapping("/")
	public String add(ModelMap model) {
		BillDetail billDetail = new BillDetail();
		model.addAttribute("BILLDETAIL", billDetail);
		model.addAttribute("ACTION", "/don-hang/saveOrUpdate");
		return "/admin/register-donhang";
	}

	@ModelAttribute(name = "INFOR")
	public List<Bills> getAllBills() {
		return billDetailService.findAllBills();
	}

	@ModelAttribute(name = "PRODUCT")
	public List<Products> findAllProducts() {
		return billDetailService.findAllProducts();
	}

	@RequestMapping("/edit/{id}")
	public String edit(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Long id) {

		Optional<BillDetail> u = billDetailService.findById(id);
		if (u.isPresent()) {
			model.addAttribute("BILLDETAIL", u.get());
			request.getSession().setAttribute("billdetails", null);
		} else {
			model.addAttribute("BILLDETAIL", new BillDetail());
		}
		model.addAttribute("ACTION", "/don-hang/update");
		return "/admin/register-donhang";
	}

	@PostMapping("/update")
	@PreAuthorize("hasPermission('', 'suadh')")
	public String Update(@ModelAttribute("BILLDETAIL") BillDetail billDetail) {
		billDetailService.save(billDetail);
		return "redirect:/don-hang/list";
	}

	@RequestMapping("/delete/{id}")
	@PreAuthorize("hasPermission('', 'xoadh')")
	public String delete(HttpServletRequest request, ModelMap model, @PathVariable(name = "id") Long id) {
		billDetailService.deleteById(id);
		request.getSession().setAttribute("billdetails", null);
		model.addAttribute("LIST_DONHANG", billDetailService.findAll());
		return "redirect:/don-hang/page/1";
	}
}
