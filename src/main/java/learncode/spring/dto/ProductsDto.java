package learncode.spring.dto;

import java.util.Date;

public class ProductsDto {

	private long id_product;
	private String details;
	private boolean highlight;
	private String image;
	private String name;
	private boolean new_product;
	private double price;
	private int sale;
	private Date created_at;
	private Date updated_at;
	private int id_category;
	private String loaimon;
	private double giamgia;

	public ProductsDto() {
		super();
	}

	public ProductsDto(long id_product, String details, boolean highlight, String image, String name,
			boolean new_product, double price, double giamgia, int sale, Date created_at, Date updated_at,
			int id_category, String loaimon) {
		super();
		this.id_product = id_product;
		this.details = details;
		this.highlight = highlight;
		this.image = image;
		this.name = name;
		this.new_product = new_product;
		this.price = price;
		this.sale = sale;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.id_category = id_category;
		this.loaimon = loaimon;
		this.giamgia = giamgia;
	}

	public long getId_product() {
		return id_product;
	}

	public void setId_product(long id_product) {
		this.id_product = id_product;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNew_product() {
		return new_product;
	}

	public void setNew_product(boolean new_product) {
		this.new_product = new_product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getGiamgia() {
		return giamgia;
	}

	public void setGiamgia(double giamgia) {
		this.giamgia = giamgia;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public int getId_category() {
		return id_category;
	}

	public void setId_category(int id_category) {
		this.id_category = id_category;
	}

	public String getLoaimon() {
		return loaimon;
	}

	public void setLoaimon(String loaimon) {
		this.loaimon = loaimon;
	}

}
