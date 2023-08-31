package vux.codejava.entity;

import javax.persistence.Embeddable;


@Embeddable
public class ContactDevice {
	private Long[] devicesId;

	public Long[] getDevicesId() {
		return devicesId;
	}

	public void setDevicesId(Long[] devicesId) {
		this.devicesId = devicesId;
	}
}
