package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.model.TinTuc;
import learncode.spring.repository.TintucRepository;


@Service
public class TinTucServiceImpl implements TinTucService {

	@Autowired
	TintucRepository TinTucRepository;

	@Override
	public TinTuc save(TinTuc entity) {
		return TinTucRepository.save(entity);
	}

	@Override
	public List<TinTuc> saveAll(List<TinTuc> entities) {
		return (List<TinTuc>)TinTucRepository.saveAll(entities);
	}

	@Override
	public Optional<TinTuc> findById(Integer id) {
		return TinTucRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return TinTucRepository.existsById(id);
	}

	@Override
	public List<TinTuc> findAll() {
		return (List<TinTuc>)TinTucRepository.findAll();
	}

	@Override
	public List<TinTuc> findAllById(List<Integer> ids) {
		return (List<TinTuc>)TinTucRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return TinTucRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		TinTucRepository.deleteById(id);
	}

	@Override
	public void delete(TinTuc entity) {
		TinTucRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<TinTuc> entities) {
		TinTucRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		TinTucRepository.deleteAll();
	}

	@Override
	public List<TinTuc> getAllTinTuc() {
		return TinTucRepository.getAllTinTuc();
	}

	@Override
	public TinTuc GetTinTucByID(int id) {
		return TinTucRepository.GetTinTucByID(id);
	}

	
	
	
}
