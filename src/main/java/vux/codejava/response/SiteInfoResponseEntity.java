package vux.codejava.response;

import vux.codejava.entity.SiteInfo;

public class SiteInfoResponseEntity {

	private Integer status;
	private String error;
	private String message;
	private SiteInfo content;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public SiteInfo getContent() {
		return content;
	}
	public void setContent(SiteInfo content) {
		this.content = content;
	}
	
}
