package learncode.spring.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import learncode.spring.model.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {
//	Product findBySku(String sku);

	List<Products> findByNameContaining(String name);

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.products\r\n"
			+ "	SET created_at=?, details=?, highlight=?, image=?, name=?, new_product=?, price=?, sale=?, updated_at=?, id_category=?\r\n"
			+ "	WHERE id=?", nativeQuery = true)
	void update(Date created_at, String details, boolean highlight, String image, String name, boolean new_product,
			double price, int sale, Date updated_at, Integer id);

	Optional<Products> findById(Integer id);

	@Query(value="select * from products where name@@ to_tsquery(?1)", nativeQuery = true)
	List<Products> findAllByName(String namesp);

	@Query(value = "Select * from products where id_category = ?1", nativeQuery = true)
	List<Products> findAllByDM(List<Long> categoryid);

	@Query(value = "select distinct ca.id, name, price, image, details,id_category, sale, highlight, new_product, updated_at, created_at from products p\r\n" + 
			"left join category ca on p.id_category = ca.id where 1=1 and name @@ to_tsquery(?1) and ca.id in (?2)", nativeQuery = true)
	List<Products> findAllByNameAndDM(String namesp, List<Long> categoryid);

}
