package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import learncode.spring.model.TinTuc;

public interface TinTucService {

	void deleteAll();

	void deleteAll(List<TinTuc> entities);

	void delete(TinTuc entity);

	void deleteById(Integer id);

	long count();

	List<TinTuc> findAllById(List<Integer> ids);

	List<TinTuc> findAll();

	boolean existsById(Integer id);

	Optional<TinTuc> findById(Integer id);

	List<TinTuc> saveAll(List<TinTuc> entities);

	TinTuc save(TinTuc entity);
	
	List<TinTuc> getAllTinTuc();
	
	public TinTuc GetTinTucByID(int id);

	

	

}
