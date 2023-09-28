package vux.codejava.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseShiftEntity {

	@Id//not null
	@GeneratedValue(strategy = GenerationType.IDENTITY)//tu tang
	private Long id;
	
	@Column(name = "user_shift")
	@CreatedBy
	private String userShift;
	
	@Column(name = "date_shift")
	@CreatedDate
	private LocalDateTime dateShift;
	
	@Column(name = "user_receive")
	@LastModifiedBy //get user login
	private String userReceive;
	
	@Column(name = "date_receive")
	@LastModifiedDate //get from system
	private LocalDateTime dateReceive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserShift() {
		return userShift;
	}

	public void setUserShift(String userShift) {
		this.userShift = userShift;
	}

	public LocalDateTime getDateShift() {
		return dateShift;
	}

	public void setDateShift(LocalDateTime dateShift) {
		this.dateShift = dateShift;
	}

	public String getUserReceive() {
		return userReceive;
	}

	public void setUserReceive(String userReceive) {
		this.userReceive = userReceive;
	}

	public LocalDateTime getDateReceive() {
		return dateReceive;
	}

	public void setDateReceive(LocalDateTime dateReceive) {
		this.dateReceive = dateReceive;
	}
	
	
}
