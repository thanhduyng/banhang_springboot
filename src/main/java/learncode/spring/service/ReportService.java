package learncode.spring.service;

import java.util.List;

import learncode.spring.model.MyItems;

public interface ReportService {

	List<MyItems> reportReceiptThongKeTongTien(int year, int month);

	List<MyItems> reportReceiptThongKeSoLuong(int year, int month);

	List<MyItems> reportReceiptThongKeSanPham(int year, int month);

	

}
