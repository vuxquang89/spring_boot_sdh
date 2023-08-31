package vux.codejava.entity;

public class DevicesOfSite {

	private Long[] devicesId;
	private Long siteId;
	
	public DevicesOfSite() {
		
	}

	public DevicesOfSite(Long[] devicesId, Long siteId) {
		this.devicesId = devicesId;
		this.siteId = siteId;
	}

	public Long[] getDevicesId() {
		return devicesId;
	}

	public void setDevicesId(Long[] devicesId) {
		this.devicesId = devicesId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	
}
