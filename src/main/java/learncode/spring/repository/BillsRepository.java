package learncode.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import learncode.spring.model.Bills;

@Repository
@Transactional
public interface BillsRepository extends JpaRepository<Bills, Long> {

	@Query(value = "select id, address, email, note, phone, display_name, quantity, total, tinhtrang\r\n"
			+ "from bills\r\n" + "where id = ?1", nativeQuery = true)
	List<Bills> findAllBillID(long id);

	@Query(value = "SELECT * FROM bills WHERE email = ?", nativeQuery = true)
	Bills findByEmail(String bill_emal);

	@Query(value = "SELECT * FROM bills WHERE id = ?", nativeQuery = true)
	Bills findById(long id);

	@Modifying
	@Query(value = "UPDATE public.bills SET display_name=?, address=?, email=?, phone=?, matkhau=? WHERE id =?;", nativeQuery = true)
	void capnhapBill(@Param("display_name") String display_name, @Param("address") String address,
			@Param("email") String email, @Param("phone") String phone, @Param("matkhau") String matkhau,
			@Param("id") long id);

	@Query(value = "SELECT * FROM bills WHERE display_name LIKE %?1%", nativeQuery = true)
	List<Bills> findByDisplayName(String displayname);
}