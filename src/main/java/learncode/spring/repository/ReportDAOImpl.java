package learncode.spring.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import learncode.spring.model.MyItems;

@Repository
public class ReportDAOImpl implements ReportDAO {

	@Autowired 
	BillDetailRepository billdetailsRepository;

	
	@Override
	public List<MyItems> reportReceiptThongKeTongTien(int year, int month) {
        List<MyItems> list = new ArrayList<>();
        int days = subDays1(year, month);
        for (int i = days; i > 0; i--) {
            MyItems myItem = new MyItems();
            String ngaythangnam = year + "-" + month + "-" + i;
            myItem.setTime(ngaythangnam);
            myItem.setValue(this.billdetailsRepository.ThongKeTongTien(toDate1(ngaythangnam)));
            list.add(myItem);
        }
        return list;
    }
	
	@Override
	public List<MyItems> reportReceiptThongKeSoLuong(int year, int month) {
        List<MyItems> list = new ArrayList<>();
        int days = subDays1(year, month);
        for (int i = days; i > 0; i--) {
            MyItems myItem = new MyItems();
            String ngaythangnam = year + "-" + month + "-" + i;
            myItem.setTime(ngaythangnam);
            myItem.setValue(this.billdetailsRepository.ThongKeSoLuong(toDate1(ngaythangnam)));
            list.add(myItem);
        }
        return list;
    }
	
	@Override
	public List<MyItems> reportReceiptThongKeSanPham(int year, int month) {
        List<MyItems> list = new ArrayList<>();
        int days = subDays1(year, month);
        for (int i = days; i > 0; i--) {
            MyItems myItem = new MyItems();
            String ngaythangnam = year + "-" + month + "-" + i;
            myItem.setTime(ngaythangnam);
            myItem.setValue(this.billdetailsRepository.ThongKeSanPham(toDate1(ngaythangnam)));
            list.add(myItem);
        }
        return list;
    }
	
	 public static Integer subDays1(Integer year, Integer month) {
	        YearMonth yearMonthObject = YearMonth.of(year, month);
	        int daysInMonth = yearMonthObject.lengthOfMonth();
	        return daysInMonth;
	    }
	
	 static final SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("yyyy-MM-dd");
	    public Date toDate1(String date, String...pattern){
	        try {
	            if (pattern.length > 0) {
	            DATE_FORMAT1.applyPattern(pattern[0]);
	            }
	            
	            return DATE_FORMAT1.parse(date);
	        } catch (ParseException ex) {
	            throw new RuntimeException(ex);
	        }
	    }
	    
	    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	    public Date toDate(String date, String...pattern){
	        try {
	            if (pattern.length > 0) {
	            DATE_FORMAT.applyPattern(pattern[0]);
	            }
	            
	            return DATE_FORMAT.parse(date);
	        } catch (ParseException ex) {
	            throw new RuntimeException(ex);
	        }
	    }
	
    
}
