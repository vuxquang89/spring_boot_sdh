package vux.codejava.entity.shift;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cables")
public class CableEntity {
	@Id
	private int id;
	
	@Column(name = "cable_name")
	private String cableName;
	
	@Column(name = "cable_code")
	private String cableCode;
	
	@OneToMany(mappedBy = "cables")
	private List<ShiftDetailEntity> shiftDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCableName() {
		return cableName;
	}

	public void setCableName(String cableName) {
		this.cableName = cableName;
	}

	public String getCableCode() {
		return cableCode;
	}

	public void setCableCode(String cableCode) {
		this.cableCode = cableCode;
	}

	public List<ShiftDetailEntity> getShiftDetails() {
		return shiftDetails;
	}

	public void setShiftDetails(List<ShiftDetailEntity> shiftDetails) {
		this.shiftDetails = shiftDetails;
	}
	
	
	

}
