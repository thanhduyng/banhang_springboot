package learncode.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "email")
	@NotBlank(message = "không được để trống")
	@Email(message = "Vui lòng nhập một địa chỉ e-mail hợp lệ")
	private String email;

	@Column(name = "password")
	@NotBlank(message = "không được để trống")
	@Length(min = 8, max = 100, message = "độ dài phải từ 8 đến 100")
	private String password;

	@Column(name = "display_name")
//	@Size(min = 3, max = 50)
	@Length(min = 2, max = 50, message = "độ dài phải từ 2 đến 50")
	private String display_name;

	@Column(name = "address")
	@NotBlank(message = "không được để trống")
	private String address;

	@Column(name = "gender")
	private boolean gender;

	public Customer() {
		super();
	}

	public Customer(int id, String email, String password, String display_name, String address, boolean gender) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.display_name = display_name;
		this.address = address;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

}
