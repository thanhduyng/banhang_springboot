package learncode.spring.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tintuc")
public class TinTuc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "tieude")
	private String tieude;

	@Column(name = "mieuta")
	private String mieuta;

	@Column(name = "noidungs")
	private String noidungs;

	@Column(name = "img")
	private String img;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayviet;

	public TinTuc() {
		super();
	}

	public TinTuc(int id, String tieude, String mieuta, String noidungs, String img, Date ngayviet) {
		super();
		this.id = id;
		this.tieude = tieude;
		this.mieuta = mieuta;
		this.noidungs = noidungs;
		this.img = img;
		this.ngayviet = ngayviet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTieude() {
		return tieude;
	}

	public void setTieude(String tieude) {
		this.tieude = tieude;
	}

	public String getMieuta() {
		return mieuta;
	}

	public void setMieuta(String mieuta) {
		this.mieuta = mieuta;
	}

	public String getNoidungs() {
		return noidungs;
	}

	public void setNoidungs(String noidungs) {
		this.noidungs = noidungs;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getNgayviet() {
		return ngayviet;
	}

	public void setNgayviet(Date ngayviet) {
		this.ngayviet = ngayviet;
	}

	
	
}
