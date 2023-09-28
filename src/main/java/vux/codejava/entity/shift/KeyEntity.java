package vux.codejava.entity.shift;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "key_shift")
public class KeyEntity {

	@Id//not null
	private Long id;
	
	@Column(name = "key_code")
	private String keyCode;
	@Column(name = "key_status")
	private Boolean keyStatus;
	
	private String username;
	
	private String district;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public Boolean getKeyStatus() {
		return keyStatus;
	}

	public void setKeyStatus(Boolean keyStatus) {
		this.keyStatus = keyStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	
}
