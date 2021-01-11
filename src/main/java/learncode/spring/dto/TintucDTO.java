package learncode.spring.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class TintucDTO {

	private int id;
	private MultipartFile img;

	@NotBlank(message = "không được để trống tieude")
	private String tieude;

	@NotBlank(message = "không được để trống mieuta")
	private String mieuta;

	private String noidungs;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayviet;

	public TintucDTO() {
		super();
	}

	public TintucDTO(int id, MultipartFile img, @NotBlank(message = "không được để trống tieude") String tieude,
			@NotBlank(message = "không được để trống mieuta") String mieuta, String noidungs, Date ngayviet) {
		super();
		this.id = id;
		this.img = img;
		this.tieude = tieude;
		this.mieuta = mieuta;
		this.noidungs = noidungs;
		this.ngayviet = ngayviet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
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

	public Date getNgayviet() {
		return ngayviet;
	}

	public void setNgayviet(Date ngayviet) {
		this.ngayviet = ngayviet;
	}

	

	
	
}
