package vux.codejava.response;

import vux.codejava.entity.shift.ShiftEntity;

public class ShiftResponse {

	private String username;
	private String district;
	private Integer action;
	private Boolean status;
	private Boolean form;
	private ShiftEntity shiftEntity;
	
	
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
	
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
	public void setAction() {
		this.action = shiftEntity.getAction();
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Boolean getForm() {
		return form;
	}
	public void setForm(Boolean form) {
		this.form = form;
	}
	public ShiftEntity getShiftEntity() {
		return shiftEntity;
	}
	public void setShiftEntity(ShiftEntity shiftEntity) {
		this.shiftEntity = shiftEntity;
	}
	
}
