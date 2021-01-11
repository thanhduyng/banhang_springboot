package learncode.spring.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapperCategory implements RowMapper<Category> {

	public Category mapRow(ResultSet rs, int rowNum) throws SQLException{
		Category categorys = new Category();
		categorys.setId(rs.getInt("id"));
		categorys.setLoaimon(rs.getString("loaimon"));
		return categorys;
	}

}
