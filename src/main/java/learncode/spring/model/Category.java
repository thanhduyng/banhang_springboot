package learncode.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "loaimon")
	@Length(min = 2, max = 50, message = "độ dài phải từ 2 đến 50")
	private String loaimon;

	public Category() {
		super();
	}

	public Category(Integer id, String loaimon) {
		super();
		this.id = id;
		this.loaimon = loaimon;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoaimon() {
		return loaimon;
	}

	public void setLoaimon(String loaimon) {
		this.loaimon = loaimon;
	}

}
