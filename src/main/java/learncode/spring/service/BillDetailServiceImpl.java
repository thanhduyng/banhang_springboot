package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import learncode.spring.model.BillDetail;
import learncode.spring.model.Bills;
import learncode.spring.model.Products;
import learncode.spring.repository.BillDetailRepository;
import learncode.spring.repository.BillsRepository;
import learncode.spring.repository.ProductRepository;

@Service
public class BillDetailServiceImpl implements BillDetailService {

	
	@Autowired
	BillDetailRepository billDetailRepository;
	
	@Autowired
	BillsRepository billRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public BillDetail save(BillDetail entity) {
		return billDetailRepository.save(entity);
	}

	@Override
	public List<BillDetail> findAll() {
		return billDetailRepository.findAll();
	}

	@Override
	public Optional<BillDetail> findById(Long id) {
		return billDetailRepository.findById(id);
	}

	@Override
	public long count() {
		return billDetailRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		billDetailRepository.deleteById(id);
	}

	@Override
	public List<Bills> findAllBills(){
		return (List<Bills>)billRepository.findAll();
	}

	@Override
	public List<Products> findAllProducts() {
		return (List<Products>)productRepository.findAll();
	}

	@Override
	public List<BillDetail> getLichsudonhang(Bills email) {
		return this.billDetailRepository.getLichSuMuaHang(email.getEmail());
	}
	
	@Override
	public List<BillDetail> getLichsudonhang2(Bills email) {
		return this.billDetailRepository.getLichSuMuaHang2(email.getEmail());
	}
	
	@Override
	public List<BillDetail> getLichsudonhang3(Bills email) {
		return this.billDetailRepository.getLichSuMuaHang3(email.getEmail());
	}
	
	 public List<BillDetail> listAll() {
	        return billDetailRepository.findAll(Sort.by("id").ascending());
	    }
	
}
