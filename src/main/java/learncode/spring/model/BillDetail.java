package learncode.spring.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "billdetail")
public class BillDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

//	@Column(name = "id_product")
//	private long id_product;
//
//	@Column(name = "id_bills")
//	private long id_bills;

	@ManyToOne()
	@JoinColumn(name = "id_product")
	private Products products;

	@ManyToOne()
	@JoinColumn(name = "id_bills")
	private Bills bills;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "total")
	private double total;

	@Column(name = "trangthai")
	private Integer trangthai;

	@Column(name = "datetime")
	@CreationTimestamp
	private LocalDateTime datetime;

	public BillDetail() {
		super();
	}

	public BillDetail(long id, Products products, Bills bills, int quantity, double total, Integer trangthai,
			LocalDateTime datetime) {
		super();
		this.id = id;
		this.products = products;
		this.bills = bills;
		this.quantity = quantity;
		this.total = total;
		this.trangthai = trangthai;
		this.datetime = datetime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Bills getBills() {
		return bills;
	}

	public void setBills(Bills bills) {
		this.bills = bills;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(Integer trangthai) {
		this.trangthai = trangthai;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	

	

}
