package vux.codejava.entity.shift;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import vux.codejava.entity.BaseShiftEntity;

@Entity
@Table(name = "receive_shift")
public class ShiftEntity extends BaseShiftEntity{

	@Column(name = "note_action")
	private String noteAction;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "key_code", unique = true)
	private String keyCode;
	
	@Column(name = "action")
	private Integer action;
	
	@Column(name = "district")
	private String district;

	public String getNoteAction() {
		return noteAction;
	}

	public void setNoteAction(String noteAction) {
		this.noteAction = noteAction;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	
}
