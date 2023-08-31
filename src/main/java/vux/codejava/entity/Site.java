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

@Entity(name = "sites")
@Table(name = "sites")
public class Site {

	@Id
	@Column(name = "site_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name = "site_name", length = 150)
	@NotBlank(message = "Tên Site không được trống")
	@Length(min = 3, max = 150, message = "Tên Site phải có từ 3 - 150 ký tự")
	private String name;
	
	@OneToMany(mappedBy = "site")
	//@JsonManagedReference
    Set<SiteInfo> siteInfo;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id", nullable = false)
	@JsonBackReference
	private District district;

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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
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
