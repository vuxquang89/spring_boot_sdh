package vux.codejava.entity.operate;


public class EventCalendar {

	private Long id;
	private String title;
	private String title_acronym;
	private String start;
	private String end;
	private String color;
	private String status;
	private Integer processingTime;
	private String note;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(Integer processingTime) {
		this.processingTime = processingTime;
	}
	public String getTitle_acronym() {
		return title_acronym;
	}
	public void setTitle_acronym(String title_acronym) {
		this.title_acronym = title_acronym;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
