package vux.codejava.entity.shift;

public class DetailValue {

	private Integer action;
	private String noteAction;
	public DetailValue() {}
	
	public DetailValue(Integer action, String noteAction) {
		
		this.action = action;
		this.noteAction = noteAction;
	}
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
	
	
}
