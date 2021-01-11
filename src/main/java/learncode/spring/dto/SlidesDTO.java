package learncode.spring.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class SlidesDTO {

	private int id;
	private MultipartFile img;
	
	@NotBlank(message = "không được để trống caption")
	private String caption;
	
	@NotBlank(message = "không được để trống content")
	private String content;

	public SlidesDTO() {
		super();
	}

	public SlidesDTO(int id, MultipartFile img, String caption, String content) {
		super();
		this.id = id;
		this.img = img;
		this.caption = caption;
		this.content = content;
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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
