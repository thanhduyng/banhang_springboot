package learncode.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.model.MyItems;
import learncode.spring.repository.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDAO reportDAO;
	

	@Override
	public List<MyItems> reportReceiptThongKeTongTien(int year, int month) {
		return reportDAO.reportReceiptThongKeTongTien(year, month);
	}


	@Override
	public List<MyItems> reportReceiptThongKeSoLuong(int year, int month) {
		return reportDAO.reportReceiptThongKeSoLuong(year, month);
	}

	@Override
	public List<MyItems> reportReceiptThongKeSanPham(int year, int month) {
		return reportDAO.reportReceiptThongKeSanPham(year, month);
	}
	
	

	
	
}
