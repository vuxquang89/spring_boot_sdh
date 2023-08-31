package vux.codejava.service;

import java.util.List;

import vux.codejava.entity.Device;
import vux.codejava.response.DeviceResponse;

public interface DeviceServices {
	List<Device> findDeviceForSiteById(Long siteId);
	
	String delete(Long deviceId);
	
	DeviceResponse getAll(String keyword, Long categoryId, int pageNo, int pageSize);
	
	boolean existsById(Long deviceId);
	
	Device findDeviceById(Long deviceId);
}
