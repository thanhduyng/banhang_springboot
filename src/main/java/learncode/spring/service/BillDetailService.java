package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import learncode.spring.model.BillDetail;
import learncode.spring.model.Bills;
import learncode.spring.model.Products;

public interface BillDetailService {

	void deleteById(Long id);

	long count();

	Optional<BillDetail> findById(Long id);

	List<BillDetail> findAll();

	BillDetail save(BillDetail entity);

	List<Bills> findAllBills();

	List<Products> findAllProducts();

	List<BillDetail> getLichsudonhang(Bills email);

	List<BillDetail> getLichsudonhang2(Bills email);

	List<BillDetail> getLichsudonhang3(Bills email);

	List<BillDetail> listAll();

}
