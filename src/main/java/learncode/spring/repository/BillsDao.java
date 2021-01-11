package learncode.spring.repository;

import org.springframework.stereotype.Repository;

import learncode.spring.model.BillDetail;
import learncode.spring.model.Bills;

@Repository
public class BillsDao extends BaseDao {

	public int AddBills(Bills bill) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO bills ");
		sql.append("( ");
		sql.append(" email, phone, display_name, address, total, quantity, ghichu, matkhau, trangthai ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("( ");
		sql.append(" '" + bill.getEmail() + "', ");
		sql.append(" '" + bill.getPhone() + "', ");
		sql.append(" '" + bill.getDisplay_name() + "', ");
		sql.append(" '" + bill.getAddress() + "', ");
		sql.append(" '" + bill.getTotal() + "', ");
		sql.append(" '" + bill.getQuantity() + "', ");
		sql.append(" '" + bill.getGhichu() + "', ");
		sql.append(" '" + bill.getMatkhau() + "', ");
		sql.append(" '" + bill.getTrangthai() + "' ");
		sql.append(") ");

		int insert = _jdbcTemplate.update(sql.toString());
		return insert;
	}
	
	public int AddBills2(Bills bill) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO bills ");
		sql.append("( ");
		sql.append(" display_name,total, quantity, ghichu, trangthai ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("( ");
		sql.append(" '" + bill.getDisplay_name() + "', ");
		sql.append(" '" + bill.getTotal() + "', ");
		sql.append(" '" + bill.getQuantity() + "', ");
		sql.append(" '" + bill.getGhichu() + "', ");
		sql.append(" '" + bill.getTrangthai() + "' ");
		sql.append(") ");
		int insert = _jdbcTemplate.update(sql.toString());
		return insert;
	}

	public long GetIDLastBills() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(id) FROM bills");
		long id = _jdbcTemplate.queryForObject(sql.toString(), new Object[] {}, Long.class);
		return id;
	}
	

	public int AddBillsDetail(BillDetail billDetail) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO billdetail ");
		sql.append("( ");
		sql.append("id_product, ");
		sql.append("id_bills, ");
		sql.append("quantity, ");
		sql.append("total, ");
		sql.append("trangthai, ");
		sql.append("datetime ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("( ");
		sql.append(" '" + billDetail.getProducts().getId() + "', ");
		sql.append(" '" + billDetail.getBills().getId() + "', ");
		sql.append(" '" + billDetail.getQuantity() + "', ");
		sql.append(" '" + billDetail.getTotal() + "', ");
		sql.append(" '" + billDetail.getTrangthai() + "', ");
		sql.append(" '" + billDetail.getDatetime() + "' ");
		sql.append(") ");

		int insert = _jdbcTemplate.update(sql.toString());
		return insert;
	}
	
	
}
