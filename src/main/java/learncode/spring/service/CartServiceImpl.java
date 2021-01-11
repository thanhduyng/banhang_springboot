package learncode.spring.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.dto.CartDto;
import learncode.spring.repository.CartDao;

@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private CartDao cartDao = new CartDao();

	public HashMap<Long, CartDto> AddCart(long id, HashMap<Long, CartDto> cart) {
		return cartDao.AddCart(id, cart);
	}

	public HashMap<Long, CartDto> EditCart(long id, int quanty, HashMap<Long, CartDto> cart) {
		return cartDao.EditCart(id, quanty, cart);
	}

	public HashMap<Long, CartDto> DeleteCart(long id, HashMap<Long, CartDto> cart) {
		return cartDao.DeleteCart(id, cart);
	}

	public int TotalQuanty(HashMap<Long, CartDto> cart) {
		return cartDao.TotalQuanty(cart);
	}

	public double TotalPrice(HashMap<Long, CartDto> cart) {
		return cartDao.TotalPrice(cart);
	}

	
}
