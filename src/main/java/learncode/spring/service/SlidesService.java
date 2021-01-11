package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import learncode.spring.model.Slides;

public interface SlidesService {
	
	void deleteAll();

	void deleteAll(List<Slides> entities);

	void delete(Slides entity);

	void deleteById(Integer id);

	long count();

	List<Slides> findAllById(List<Integer> ids);

	List<Slides> findAll();

	boolean existsById(Integer id);

	Optional<Slides> findById(Integer id);

	List<Slides> saveAll(List<Slides> entities);

	Slides save(Slides entity);
}
