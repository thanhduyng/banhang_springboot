package learncode.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import learncode.spring.dto.CartDto;
import learncode.spring.model.Bills;

@Service
public interface IBillsService {
	
	public int AddBills(Bills bill);

	public void AddBillsDetail(HashMap<Long, CartDto> carts);

	void deleteAll();

	void delete(Bills entity);

	void deleteById(Long id);

	long count();

	boolean existsById(Long id);

	Optional<Bills> findById(Long id);

	List<Bills> saveAll(List<Bills> entities);

	List<Bills> findAllById(List<Long> ids);

	List<Bills> findAllBillsID(long id);

	Bills save(Bills entity);

	List<Bills> findAll();

	public boolean checkLogin(String email, String matkhau);

	public void capnhapBill(Bills b);
	
	


}
