package learncode.spring.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductsDtoMapper implements RowMapper<ProductsDto> {

	public ProductsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductsDto products = new ProductsDto();
		products.setId_product(rs.getLong("id_product"));
		products.setCreated_at(rs.getDate("created_at"));
		products.setDetails(rs.getString("details"));
		products.setHighlight(rs.getBoolean("highlight"));
		products.setImage(rs.getString("image"));
		products.setName(rs.getString("name"));
		products.setNew_product(rs.getBoolean("new_product"));
		products.setPrice(rs.getDouble("price"));
		products.setSale(rs.getInt("sale"));
		products.setUpdated_at(rs.getDate("updated_at"));
		products.setId_category(rs.getInt("id_category"));
		products.setLoaimon(rs.getString("loaimon"));
		products.setGiamgia(rs.getDouble("giamgia"));
		return products;
	}
}
