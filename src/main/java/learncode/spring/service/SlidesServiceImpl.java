package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.model.Slides;
import learncode.spring.repository.SlidesRepository;

@Service
public class SlidesServiceImpl implements SlidesService {

	@Autowired
	SlidesRepository slidesRepository;

	@Override
	public Slides save(Slides entity) {
		return slidesRepository.save(entity);
	}

	@Override
	public List<Slides> saveAll(List<Slides> entities) {
		return (List<Slides>)slidesRepository.saveAll(entities);
	}

	@Override
	public Optional<Slides> findById(Integer id) {
		return slidesRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return slidesRepository.existsById(id);
	}

	@Override
	public List<Slides> findAll() {
		return (List<Slides>)slidesRepository.findAll();
	}

	@Override
	public List<Slides> findAllById(List<Integer> ids) {
		return (List<Slides>)slidesRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return slidesRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		slidesRepository.deleteById(id);
	}

	@Override
	public void delete(Slides entity) {
		slidesRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<Slides> entities) {
		slidesRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		slidesRepository.deleteAll();
	}
	
}
