package vux.codejava.entity;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "sites_info")
@Table(name = "sites_info")
public class SiteInfo {

	@Id
	@Column(name = "site_info_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "site_id")
	//@JsonBackReference
	private Site site;
	
	@ManyToOne
	@JoinColumn(name = "device_id")
	//@JsonBackReference
	private Device device;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "slot")
	private String slot;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "quantity")
	private Integer quantity = 1;
	/*
	@Embedded
    private ContactDevice contactDevice;
    */
	
	@OneToMany(mappedBy = "siteInfo", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private Set<SiteImage> siteImages;
	
	public SiteInfo() {}
	
	/*
	public SiteInfo(Site site, ContactDevice contactDevice) {
		this.site = site;
		this.contactDevice = contactDevice;
	}
	*/
	public SiteInfo(Site site, Device device) {
		this.site = site;
		this.device = device;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Set<SiteImage> getSiteImages() {
		return siteImages;
	}

	public void setSiteImages(Set<SiteImage> siteImages) {
		this.siteImages = siteImages;
	}

	/*
	public ContactDevice getContactDevice() {
		return contactDevice;
	}

	public void setContactDevice(ContactDevice contactDevice) {
		this.contactDevice = contactDevice;
	}
	*/
	
}
