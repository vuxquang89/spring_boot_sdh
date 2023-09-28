package vux.codejava.entity.shift;

public class ShiftRequest {

	private Integer action;
	private String noteAction;
	private String note;
	private String district;
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
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
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	
}
