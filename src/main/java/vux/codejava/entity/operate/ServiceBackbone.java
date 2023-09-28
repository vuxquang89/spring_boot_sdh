package vux.codejava.entity.operate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "service_backbone")
@Table(name = "service_backbone")
public class ServiceBackbone {
	
	@Id
	@Column(name = "service_backbone_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "service_backbone_name")
	private String name;
	
	//@OneToMany(cascade = CascadeType.ALL,
	//		mappedBy = "serviceBackbone")
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
	/*
	public Set<OperationalStatistics> getOperationalStatistics() {
		return operationalStatistics;
	}

	public void setOperationalStatistics(Set<OperationalStatistics> operationalStatistics) {
		this.operationalStatistics = operationalStatistics;
	}
	*/
}
