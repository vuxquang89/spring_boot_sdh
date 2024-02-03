package vux.codejava.request;

import org.springframework.web.multipart.MultipartFile;

public class ExcellentIndividualRequest {

	private Integer id;
	private Long userId;
	private String fullName;
	private String content;
	private MultipartFile imageAvatar;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MultipartFile getImageAvatar() {
		return imageAvatar;
	}
	public void setImageAvatar(MultipartFile imageAvatar) {
		this.imageAvatar = imageAvatar;
	}
	
}
