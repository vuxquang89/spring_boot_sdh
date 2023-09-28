package vux.codejava.entity.operate;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "operational_statistics")
@Table(name = "operational_statistics")
public class OperationalStatistics {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_create")
	private String userCreate;
	
	@Column(name="user_update")
	private String userUpdate;
	
	//@ManyToOne
	//@JoinColumn(name = "user_id")
	//@JsonBackReference
	//private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cable_link_id")
	@JsonIgnore
	//@JsonBackReference
	private CableLink cableLink;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "event_id")
	@JsonIgnore
	//@JsonBackReference
	private Event event;
	
	@Column(name = "start_time")
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;
	
	@Column(name = "end_time")
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endTime;
	
	@Column(name = "processing_time")
	private Integer processingTime;
	
	@Column(name = "note")
	//@NotBlank(message = "Cần nhập nội dung ghi chú")
	//@Length(min = 50, message = "Ghi chú phải có từ 50 ký tự")
	private String note;
	
	private String subject;
	
	@Column(name = "create_time")
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createTime;
	
	@Column(name = "update_time")
	//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime updateTime;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "enable")
	private boolean enable = true;
	
	@Column(name = "action")
	private boolean action = true;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "service_internal_id")
	@JsonIgnore
	//@JsonBackReference
	private ServiceInternal serviceInternal;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "service_customer_id")
	@JsonIgnore
	//@JsonBackReference
	private ServiceCustomer serviceCustomer;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "service_backbone_id")
	@JsonIgnore
	//@JsonBackReference
	private ServiceBackbone serviceBackbone;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
	@JoinColumn(name = "status_id")
	//@JsonBackReference
	private Status status;
	
	public OperationalStatistics() {}

	public OperationalStatistics(String userUpdate, CableLink cableLink, Event event, LocalDateTime startTime, LocalDateTime endTime,
			Integer processingTime, Status status, String note, LocalDateTime createTime, LocalDateTime updateTime, boolean enable,
			ServiceInternal serviceInternal, ServiceCustomer serviceCustomer, ServiceBackbone serviceBackbone) {
		this.userUpdate = userUpdate;
		this.cableLink = cableLink;
		this.event = event;
		this.startTime = startTime;
		this.endTime = endTime;
		this.processingTime = processingTime;
		this.status = status;
		this.note = note;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.enable = enable;
		this.serviceInternal = serviceInternal;
		this.serviceCustomer = serviceCustomer;
		this.serviceBackbone = serviceBackbone;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getUserUpdate() {
		return userUpdate;
	}

	public void setUserUpdate(String userUpdate) {
		this.userUpdate = userUpdate;
	}

	/*
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	*/
	/*
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	*/
	public CableLink getCableLink() {
		return cableLink;
	}

	public void setCableLink(CableLink cableLink) {
		this.cableLink = cableLink;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public LocalDateTime getLocalStartTime() {
		return startTime;
	}
	
	public String getStartTime() {
		return startTime.toString();
	}

	public void setStartTime(String startTime) {
		this.startTime = vux.codejava.lib.Convert.stringToDateTime(startTime);
	}
	
	public LocalDateTime getLocalEndTime() {
		return endTime;
	}

	public String getEndTime() {
		return endTime.toString();
	}

	public void setEndTime(String endTime) {
		this.endTime = vux.codejava.lib.Convert.stringToDateTime(endTime);
	}

	public Integer getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(Integer processingTime) {
		this.processingTime = processingTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public ServiceInternal getServiceInternal() {
		return serviceInternal;
	}

	public void setServiceInternal(ServiceInternal serviceInternal) {
		this.serviceInternal = serviceInternal;
	}

	public ServiceCustomer getServiceCustomer() {
		return serviceCustomer;
	}

	public void setServiceCustomer(ServiceCustomer serviceCustomer) {
		this.serviceCustomer = serviceCustomer;
	}

	public ServiceBackbone getServiceBackbone() {
		return serviceBackbone;
	}

	public void setServiceBackbone(ServiceBackbone serviceBackbone) {
		this.serviceBackbone = serviceBackbone;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}
	
	
	//https://www.bezkoder.com/jpa-one-to-many/
}
