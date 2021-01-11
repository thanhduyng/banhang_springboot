package learncode.spring.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import learncode.spring.dto.ProductsDto;
import learncode.spring.dto.ProductsDtoMapper;

@Repository
public class ProductsDao extends BaseDao {

	private final boolean YES = true;
	private final boolean NO = false;

	private StringBuffer SqlString() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("p.id as id_product ");
		sql.append(",p.created_at ");
		sql.append(",p.details ");
		sql.append(",p.highlight ");
		sql.append(",p.image ");
		sql.append(",p.name ");
		sql.append(",p.new_product ");
		sql.append(",p.price ");
		sql.append(",p.giamgia ");
		sql.append(",p.sale ");
		sql.append(",p.updated_at ");
		sql.append(",p.id_category ");
		sql.append(",c.loaimon ");
		sql.append("FROM ");
		sql.append("products AS p ");
		sql.append("INNER JOIN category AS c ");
		sql.append("ON p.id_category = c.id ");
		return sql;
	}

	private String SqlSale() {
		StringBuffer sql = SqlString();
		sql.append("WHERE p.sale = 1 ");
		sql.append("ORDER BY random( ) ");
		sql.append("LIMIT 6 ");
		return sql.toString();
	}
	
	public List<ProductsDto> GetSale() {
		String sql = SqlSale();
		List<ProductsDto> listProduct = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProduct;
	}

	private String SqlProducts(boolean newProduct, boolean highLight) {
		StringBuffer sql = SqlString();
		sql.append("WHERE 1 = 1 ");
		if (highLight) {
			sql.append("AND p.highlight = true ");
		}
		if (newProduct) {
			sql.append("AND p.new_product = true ");
		}
		sql.append("ORDER BY random( ) ");
		if (highLight) {
			sql.append("LIMIT 16 ");
		}
		if (newProduct) {
			sql.append("LIMIT 9 ");
		}
		return sql.toString();
	}

	public List<ProductsDto> HighLightDataProducts() {
		String sql = SqlProducts(NO, YES);
		List<ProductsDto> listProducts = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProducts;
	}

	public List<ProductsDto> NewDataProducts() {
		String sql = SqlProducts(YES, NO);
		List<ProductsDto> listProducts = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProducts;
	}

	public List<ProductsDto> GetProductByID(int id) {
		String sql = SqlProductByID(id);
		List<ProductsDto> listProduct = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProduct;
	}

	public ProductsDto FindProductByID(long id) {
		String sql = SqlProductByID(id);
		ProductsDto product = _jdbcTemplate.queryForObject(sql, new ProductsDtoMapper());
		return product;
	}

	private String SqlProductByID(long id) {
		StringBuffer sql = SqlString();
		sql.append("WHERE 1=1 ");
		sql.append("AND p.id = " + id + " ");
		sql.append("LIMIT 1 ");
		return sql.toString();
	}

	private StringBuffer SqlProductsByID(int id) {
		StringBuffer sql = SqlString();
		sql.append("WHERE 1=1 ");
		sql.append("AND id_category = " + id + " ");
		return sql;
	}

	public List<ProductsDto> GetAllProductsByID(int id) {
		String sql = SqlProductsByID(id).toString();
		List<ProductsDto> listProducts = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProducts;
	}

	private StringBuffer SqlProducts() {
		StringBuffer sql = SqlString();
		return sql;
	}

	public List<ProductsDto> GetAllProducts() {
		String sql = SqlProducts().toString();
		List<ProductsDto> listProducts = _jdbcTemplate.query(sql, new ProductsDtoMapper());
		return listProducts;
	}

//	private String SqlProductsPaginate(int id) {
//		StringBuffer sql = SqlProductsByID(id);
////		sql.append("LIMIT "+ totalPage +" "+"OFFSET"+" "+start);
//		return sql.toString();
//	}

//	public List<ProductsDto> GetDataProductsPaginate(int id) {
//		StringBuffer sqlGetDataByID = SqlProductsByID(id);
//		List<ProductsDto> listProductsByID = _jdbcTemplate.query(sqlGetDataByID.toString(), new ProductsDtoMapper());
//		List<ProductsDto> listProducts = new ArrayList<ProductsDto>();
//		
//		return listProducts;
//	}

}
