package vux.codejava.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "devices")
@Table(name = "devices")
public class Device {

	@Id
	@Column(name = "device_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name = "device_name", length = 250, unique = true)
	@NotBlank(message = "Tên thiết bị không được trống")
	@Length(min = 3, max = 250, message = "Tên thiết bị phải có từ 3 - 250 ký tự")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonBackReference
	private Category category;
	
	@OneToMany(mappedBy = "device")
	//@JsonManagedReference
    Set<SiteInfo> siteInfo;	
	
	public Device() {}
	
	public Device(Long id, String name) {
		this.id= id;
		this.name = name;
	}
	
	public Device(String name) {
		this.name = name;
	}
	
	public Device(String name, Category category) {
		this.name = name;
		this.category = category;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	/*
	public Set<SiteInfo> getSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(Set<SiteInfo> siteInfo) {
		this.siteInfo = siteInfo;
	}
	*/
	
	
}
