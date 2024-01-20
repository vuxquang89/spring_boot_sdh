package vux.codejava.entity.shift;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vux.codejava.entity.BaseShiftEntity;

@Entity
@Table(name = "receive_shift")
public class ShiftEntity extends BaseShiftEntity{

	@Column(name = "note_receive")
	private String noteReceive;
	
	@Column(name = "user_change_note_receive")
	private String userChangeNoteReceive;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "key_code", unique = true)
	private String keyCode;
	
	@Column(name = "action")
	private Integer action;
	
	@Column(name = "district")
	private String district;
	
	@OneToMany(mappedBy="shift")
    private List<ShiftDetailEntity> shiftDetails;

	public String getNoteReceive() {
		return noteReceive;
	}

	public void setNoteReceive(String noteReceive) {
		this.noteReceive = noteReceive;
	}

	public String getUserChangeNoteReceive() {
		return userChangeNoteReceive;
	}

	public void setUserChangeNoteReceive(String userChangeNoteReceive) {
		this.userChangeNoteReceive = userChangeNoteReceive;
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

	public List<ShiftDetailEntity> getShiftDetails() {
		return shiftDetails;
	}

	public void setShiftDetails(List<ShiftDetailEntity> shiftDetails) {
		this.shiftDetails = shiftDetails;
	}
	
	@Override
	public String toString() {
		String noteAction = "";
		for(ShiftDetailEntity shiftDetail : shiftDetails) {
			if(shiftDetail.getAction() == 1) {
				noteAction += shiftDetail.getCables().getCableName() + " : " + shiftDetail.getNoteAction() + "\n";
			}
		}
		return noteAction;
	}
	
	
}
