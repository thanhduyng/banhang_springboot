package learncode.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.comon.Xuly;
import learncode.spring.dto.CartDto;
import learncode.spring.model.Bills;
import learncode.spring.repository.BillsDao;
import learncode.spring.repository.BillsRepository;

@Service
public class BillsServiceImpl implements IBillsService {

	@Autowired
	private BillsDao billsDao;

	@Autowired
	private BillsRepository billsRepository;

	public int AddBills(Bills bill) {
		return billsDao.AddBills(bill);
	}

	public int AddBills2(Bills bill) {
		return billsDao.AddBills2(bill);
	}

	public void AddBillsDetail(HashMap<Long, CartDto> carts) {
//
//		long idBills = billsDao.GetIDLastBills();
//
//		for (Map.Entry<Long, CartDto> itemCart : carts.entrySet()) {
//			BillDetail billDetail = new BillDetail();
////			billDetail.setId_bills(idBills);
////			billDetail.setId_product(itemCart.getValue().getProduct().getId_product());
//			
//			billDetail.setBills(billsRepository.findById(idBills));
//			billDetail.setProducts(productRepository.findById(itemCart.getValue().getProduct().getId_product()).get());
//			billDetail.setQuantity(itemCart.getValue().getQuanty());
//			billDetail.setTotal(itemCart.getValue().getTotalPrice());
//			billDetail.setTinhtrang((Integer) 1);
//			billDetail.setDatetime(LocalDateTime.now());
//			billsDao.AddBillsDetail(billDetail);
//		}
	}

	@Override
	public Bills save(Bills entity) {
		return billsRepository.save(entity);
	}

	@Override
	public List<Bills> findAllBillsID(long id) {
		return this.billsRepository.findAllBillID(id);
	}

	@Override
	public List<Bills> findAll() {
		return billsRepository.findAll();
	}

	@Override
	public List<Bills> findAllById(List<Long> ids) {
		return billsRepository.findAllById(ids);
	}

	@Override
	public List<Bills> saveAll(List<Bills> entities) {
		return billsRepository.saveAll(entities);
	}

	@Override
	public Optional<Bills> findById(Long id) {
		return billsRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return billsRepository.existsById(id);
	}

	@Override
	public long count() {
		return billsRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		billsRepository.deleteById(id);
	}

	@Override
	public void delete(Bills entity) {
		billsRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		billsRepository.deleteAll();
	}

	@Override
	public boolean checkLogin(String email, String matkhau) {
		Bills bi = this.billsRepository.findByEmail(email);
		if (null != bi && Xuly.checkMd5(matkhau, bi.getMatkhau())) {
			return true;
		}
		return false;
	}

	@Override
	public void capnhapBill(Bills b) {
		billsRepository.capnhapBill(b.getDisplay_name(), b.getAddress(), b.getEmail(), b.getPhone(), b.getMatkhau(), b.getId());

	}

}
