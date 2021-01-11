package learncode.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import learncode.spring.model.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

	@Query(value = "SELECT * FROM billdetail bd LEFT JOIN bills b \n"
			+ "ON b.id = bd.id_bills WHERE b.email = ? AND bd.trangthai = 1", nativeQuery = true)
	List<BillDetail> getLichSuMuaHang(@Param("email") String email);

	@Query(value = "select * from billdetail order by trangthai", nativeQuery = true)
	List<BillDetail> findAll();

	@Query(value = "SELECT * FROM billdetail bd LEFT JOIN bills b \n"
			+ "ON b.id = bd.id_bills WHERE b.email = ? AND bd.trangthai = 2", nativeQuery = true)
	List<BillDetail> getLichSuMuaHang2(@Param("email") String email);

	@Query(value = "SELECT * FROM billdetail bd LEFT JOIN bills b \n"
			+ "ON b.id = bd.id_bills WHERE b.email = ? AND bd.trangthai = 3", nativeQuery = true)
	List<BillDetail> getLichSuMuaHang3(@Param("email") String email);

	
	
	@Query(value = "SELECT sum(total) tongtien \n" + 
			"FROM billdetail \n" + 
			"WHERE datetime = ?", nativeQuery = true)
	Integer ThongKeTongTien(@Param("thang") Date thang);

	@Query(value = "SELECT sum(quantity)\n" + 
			"FROM billdetail \n" + 
			"WHERE datetime = ?", nativeQuery = true)
	Integer ThongKeSoLuong(@Param("thang") Date thang);
	

	@Query(value = "SELECT count(id)\n" + 
			"FROM billdetail \n" + 
			"where datetime = ?", nativeQuery = true)
	Integer ThongKeSanPham(@Param("thang") Date thang);

	

}
