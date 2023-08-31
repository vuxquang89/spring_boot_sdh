package vux.codejava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vux.codejava.entity.Device;
import vux.codejava.repository.DeviceRepository;
import vux.codejava.response.DeviceResponse;
import vux.codejava.service.DeviceServices;

@Service
public class DeviceServiesImple implements DeviceServices{

	@Autowired
	private DeviceRepository repo;
	
	@Override
	public List<Device> findDeviceForSiteById(Long siteId) {
		return repo.findDeviceForSiteById(siteId);
	}
	@Override
	public String delete(Long deviceId) {
		Device device = repo.findDeviceById(deviceId);
		repo.delete(device);
		return "done";
	}
	
	@Override
	public DeviceResponse getAll(String keyword, Long categoryId, int pageNo, int pageSize) {
		//Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Page<Device> pages = repo.getAll(keyword, categoryId, pageable);
		List<Device> listDevice = pages.getContent();
		DeviceResponse deviceResponse = new DeviceResponse();
		deviceResponse.setContent(listDevice);
		deviceResponse.setPageNo(pages.getNumber());
		deviceResponse.setPageSize(pages.getSize());
		deviceResponse.setTotalItems(pages.getTotalElements());
		deviceResponse.setTotalPages(pages.getTotalPages());
		return deviceResponse;
	}
	@Override
	public boolean existsById(Long deviceId) {
		return repo.existsById(deviceId);
	}
	
	@Override
	public Device findDeviceById(Long deviceId) {
		return repo.findDeviceById(deviceId);
	}
}

