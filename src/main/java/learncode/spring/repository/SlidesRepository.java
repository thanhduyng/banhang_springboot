package learncode.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import learncode.spring.model.Slides;

public interface SlidesRepository extends JpaRepository<Slides, Integer> {

}
