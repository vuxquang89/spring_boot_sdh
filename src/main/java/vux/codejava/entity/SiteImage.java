package vux.codejava.entity;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "site_iamges")
@Table(name = "site_images")
public class SiteImage {
	
	@Id
	@Column(name = "site_image_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_info_id")
	@JsonBackReference
	private SiteInfo siteInfo;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file_name_100")
	private String fileName100;
	
	@Column(name = "path_name")
	private String pathName;

	public SiteImage() {
		
	}
	
	public SiteImage(String fileName, String fileName100, String pathName, SiteInfo siteInfo) {
		this.siteInfo = siteInfo;
		this.fileName = fileName;
		this.fileName100 = fileName100;
		this.pathName = pathName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SiteInfo getSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(SiteInfo siteInfo) {
		this.siteInfo = siteInfo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName100() {
		return fileName100;
	}

	public void setFileName100(String fileName100) {
		this.fileName100 = fileName100;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	@Transient
	public String getImagePath() {
		if(fileName == null || id == null) return "";
		
		return "/" + fileName;
	}

}
