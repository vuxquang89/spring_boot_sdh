package vux.codejava.entity.shift;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "receive_shift_detail")
public class ShiftDetailEntity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    @JoinColumn(name="receive_shift_id", nullable=false)
    private ShiftEntity shift;
	
	@ManyToOne
    @JoinColumn(name="cables_id", nullable=false)
    private CableEntity cables;
	
	@Column(name = "action")
	private Integer action;
	
	@Column(name = "note_action")
	private String noteAction;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShiftEntity getShift() {
		return shift;
	}

	public void setShift(ShiftEntity shift) {
		this.shift = shift;
	}

	
	public CableEntity getCables() {
		return cables;
	}

	public void setCables(CableEntity cables) {
		this.cables = cables;
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
