package vux.codejava.entity.shift;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import vux.codejava.entity.BaseShiftEntity;

@Entity
@Table(name = "receive_shift")
public class ShiftEntity extends BaseShiftEntity{

	@Column(name = "note_shift")
	private String noteShift;
	
	@Column(name = "note")
	private String note;

	public String getNoteShift() {
		return noteShift;
	}

	public void setNoteShift(String noteShift) {
		this.noteShift = noteShift;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
