package vux.codejava.entity.operate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name= "cable_link")
@Table(name = "cable_link")
public class CableLink {

	@Id
	@Column(name = "cable_link_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cable_link_name")
	private String name;
	
	private String district;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cable_type_id")
	@JsonIgnore
	//@JsonBackReference
	private CableType cableType;
	
	//@OneToMany(mappedBy = "cableLink")	
	//@JsonManagedReference
	//private Set<OperationalStatistics> operationalStatistics;

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

	public CableType getCableType() {
		return cableType;
	}

	public void setCableType(CableType cableType) {
		this.cableType = cableType;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	/*
	public Set<OperationalStatistics> getOperationalStatistics() {
		return operationalStatistics;
	}

	public void setOperationalStatistics(Set<OperationalStatistics> operationalStatistics) {
		this.operationalStatistics = operationalStatistics;
	}
	*/
	
}
