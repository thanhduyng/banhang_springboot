package learncode.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import learncode.spring.model.MyItems;
import learncode.spring.repository.BillDetailRepository;
import learncode.spring.service.ReportService;

@Controller
@RequestMapping("/thongke")
public class ThongKeController {
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	BillDetailRepository billdetailsRepository;

	@GetMapping("/tongtien")
	public String tongtien() {
		return "thongketongtienthang";
	}
	
	@GetMapping("/tongtientheothang")
	public String listtongtien(HttpSession session, ModelMap model, 
			@RequestParam("tongtienthang") Integer tongtienthang, @RequestParam("tongtiennam") Integer tongtiennam) {
		session.setAttribute("THANG", tongtienthang);
		session.setAttribute("NAM", tongtiennam);
		if(tongtienthang == null || tongtiennam == null) {
			return "thongketongtienthang";
		}
		List<MyItems> listItem = this.reportService.reportReceiptThongKeTongTien(tongtiennam, tongtienthang);
		System.out.println("Aaa"+listItem.toString());
		model.addAttribute("TONGTIEN", listItem);
		return "thongketongtienthang";
	}
	
	@GetMapping("/soluong")
	public String soluong() {
		return "thongkesoluongthang";
	}
	
	@GetMapping("/soluongthang")
	public String listsoluong(HttpSession session, ModelMap model, 
			@RequestParam("soluongthang") Integer soluongthang, @RequestParam("soluongnam") Integer soluongnam) {
		session.setAttribute("THANG", soluongthang);
		session.setAttribute("NAM", soluongnam);
		if(soluongthang == null || soluongnam == null) {
			return "thongkesoluongthang";
		}
		List<MyItems> listItem = this.reportService.reportReceiptThongKeSoLuong(soluongnam, soluongthang);
		System.out.println("Aaa"+listItem.toString());
		model.addAttribute("SOLUONG", listItem);
		return "thongkesoluongthang";
	}
	
	@GetMapping("/sanpham")
	public String sanpham() {
		return "thongkesoluongsanphamthang";
	}
	
	@GetMapping("/sanphamthang")
	public String listsanpham(HttpSession session, ModelMap model, 
			@RequestParam("sanphamthang") Integer sanphamthang, @RequestParam("sanphamnam") Integer sanphamnam) {
		session.setAttribute("THANG", sanphamthang);
		session.setAttribute("NAM", sanphamnam);
		if(sanphamthang == null || sanphamnam == null) {
			return "thongkesoluongsanphamthang";
		}
		List<MyItems> listItem = this.reportService.reportReceiptThongKeSanPham(sanphamnam, sanphamthang);
		System.out.println("Aaa"+listItem.toString());
		model.addAttribute("SANPHAM", listItem);
		return "thongkesoluongsanphamthang";
	}
}
