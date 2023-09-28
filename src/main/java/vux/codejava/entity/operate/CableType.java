package vux.codejava.entity.operate;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name="cable_type")
@Table(name="cable_type")
public class CableType {

	@Id
	@Column(name="cable_type_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cable_type_name")
	private String name;
	
	@OneToMany(mappedBy = "cableType")
	//@JsonManagedReference
	private Set<CableLink> cableLink;

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

	public Set<CableLink> getCableLink() {
		return cableLink;
	}

	public void setCableLink(Set<CableLink> cableLink) {
		this.cableLink = cableLink;
	}
	
	
}
