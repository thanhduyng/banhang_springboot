package learncode.spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
//	@Length(min = 2, max = 50, message = "độ dài phải từ 2 đến 50")
	private String name;
	
	@Column(name="price")
	private double price;
	
	@Column(name="image")
	private String image;
	
	@ManyToOne()
	@JoinColumn(name="id_category")
	private Category categorys;
	
	@Column(name="sale")
	private int sale;
	
	@Column(name="highlight")
	private boolean highlight;
	
	@Column(name="new_product")
	private boolean new_product;
	
	@Column(name="details")
	private String details;
	
	@Column(name="giamgia")
	private double giamgia;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date created_at;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date updated_at;

	public Products() {
		super();
	}

	public Products(Long id, String name, double price, String image, Category categorys, int sale, boolean highlight,
			boolean new_product, String details, double giamgia, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.categorys = categorys;
		this.sale = sale;
		this.highlight = highlight;
		this.new_product = new_product;
		this.details = details;
		this.giamgia = giamgia;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategorys() {
		return categorys;
	}

	public void setCategorys(Category categorys) {
		this.categorys = categorys;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	public boolean isHighlight() {
		return highlight;
	}

	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	public boolean isNew_product() {
		return new_product;
	}

	public void setNew_product(boolean new_product) {
		this.new_product = new_product;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getGiamgia() {
		return giamgia;
	}

	public void setGiamgia(double giamgia) {
		this.giamgia = giamgia;
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

	


	
	
}
