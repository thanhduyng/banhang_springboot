package learncode.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "bills")
@Proxy(lazy = false)
public class Bills {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "email")
	@NotBlank(message = "không được để trống")
	@Email(message = "Vui lòng nhập một địa chỉ e-mail hợp lệ")
	private String email;

	@Column(name = "phone")
	@NotBlank(message = "không được để trống")
	private String phone;

	@Column(name = "display_name")
	@Length(min = 2, max = 50, message = "độ dài phải từ 2 đến 50")
	private String display_name;

	@Column(name = "address")
	@NotBlank(message = "không được để trống")
	private String address;
	
	@Column(name = "matkhau")
	@NotBlank(message = "không được để trống")
	@Length(min = 8, max = 100, message = "độ dài phải từ 8 đến 100")
	private String matkhau;

	@Column(name = "total")
	private double total;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "ghichu")
	private String ghichu;
	
	@Column(name = "trangthai")
	private int trangthai;

	public Bills() {
		super();
	}

	public Bills(long id, String email, String phone, String display_name, String address, String matkhau, double total,
			int quantity, int trangthai, String ghichu) {
		super();
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.display_name = display_name;
		this.address = address;
		this.matkhau = matkhau;
		this.total = total;
		this.quantity = quantity;
		this.trangthai = trangthai;
		this.ghichu = ghichu;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMatkhau() {
		return matkhau;
	}

	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}
	
	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}


}
