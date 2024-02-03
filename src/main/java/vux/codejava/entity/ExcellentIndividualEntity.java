package vux.codejava.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "excellent_individual")
@Table(name = "excellent_individual")
public class ExcellentIndividualEntity {

	@Id//not null
	@GeneratedValue(strategy = GenerationType.IDENTITY)//tu tang
	private Integer id;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "image_avatar")
	private String imageAvatar;
	
	@Column(name = "image_avatar_resize")
	private String imageAvatarResize;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getImageAvatar() {
		return imageAvatar;
	}

	public void setImageAvatar(String imageAvatar) {
		this.imageAvatar = imageAvatar;
	}

	public String getImageAvatarResize() {
		return imageAvatarResize;
	}

	public void setImageAvatarResize(String imageAvatarResize) {
		this.imageAvatarResize = imageAvatarResize;
	}
	
	
}
