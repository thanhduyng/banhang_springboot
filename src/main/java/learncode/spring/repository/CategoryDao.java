package learncode.spring.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import learncode.spring.model.Category;
import learncode.spring.model.MapperCategory;




@Repository
public class CategoryDao extends BaseDao {

//	@Query
//	List<Category> findByloaimonStartingWith(String loaimon);

	public List<Category> GetDataCategorys(){
		List<Category> list = new ArrayList<Category>();
		String sql ="SELECT * FROM category";
		list = _jdbcTemplate.query(sql, new MapperCategory());
		return list;
	}

	
}
