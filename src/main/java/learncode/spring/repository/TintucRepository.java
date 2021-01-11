package learncode.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import learncode.spring.model.TinTuc;

public interface TintucRepository extends JpaRepository<TinTuc, Integer> {

	@Query(value = "select * from tintuc order by random( ) limit 3", nativeQuery = true)
	List<TinTuc> getAllTinTuc();

	@Query(value = "select * from tintuc where id = ?", nativeQuery = true)
	TinTuc GetTinTucByID(int id);
	
	@Query(value = "SELECT * FROM tintuc WHERE tintuc.ngayviet < CURRENT_DATE\r\n" + 
			"ORDER BY tintuc.ngayviet DESC", nativeQuery = true)
	List<TinTuc> findAll();

	
			
	
}
