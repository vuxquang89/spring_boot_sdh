package vux.codejava.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "districts")
@Table(name = "districts")
public class District {
	@Id
	@Column(name = "district_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name = "district_name", length = 150, unique = true)
	@NotBlank(message = "Tên khu vực không được trống")
	@Length(min = 5, max = 150, message = "Tên khu vực phải có từ 5 - 150 ký tự")
	private String name;
	
	@OneToMany(mappedBy = "district", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private Set<Site> sites;

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

	public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}

		
}
