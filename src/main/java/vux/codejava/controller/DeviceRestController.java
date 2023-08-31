package vux.codejava.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vux.codejava.entity.Category;
import vux.codejava.entity.Device;
import vux.codejava.repository.DeviceRepository;
import vux.codejava.response.DeviceResponse;
import vux.codejava.service.DeviceServices;
import vux.codejava.util.AppConstants;

@RestController
@RequestMapping("/api")
public class DeviceRestController {

	@Autowired
	private DeviceRepository deviceRepo;
	
	@Autowired
	private DeviceServices services;
	
	@GetMapping("/device/category/{id}/page")
	public ResponseEntity<DeviceResponse> getSiteInfo(
			@PathVariable("id") Long categoryId,
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_PAGE_SEARCH, required = false) String keyWord,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {
		
		return ResponseEntity.ok(services.getAll(keyWord, categoryId, pageNo, pageSize));
	}
	
	@GetMapping("/device/list/{id}")
	public ResponseEntity<List<Device>> listByCategory(@PathVariable("id") Long categoryId){
		System.out.println("Category ID : " + categoryId);
		return ResponseEntity.ok(deviceRepo.findByCategory(new Category(categoryId)));
	}
	
	@GetMapping("/device/site/list/{id}")
	public ResponseEntity<List<Device>> listBySite(@PathVariable("id") Long siteId){
		
		//return deviceRepo.findAll();
		return ResponseEntity.ok(services.findDeviceForSiteById(siteId));
	}
	
	@GetMapping("/device/list")
	public ResponseEntity<List<Device>> listAll(){
		System.out.println("Load Device REST API..");
		return ResponseEntity.ok(deviceRepo.findAll());
	}
	
	@PostMapping("/device/save")
	public ResponseEntity<Device> save(@RequestBody @Valid Device device) {
		System.out.println("save Device REST API....");
		System.out.println(device.getName());
		System.out.println(device.getCategory().getName());
		
		Device savedDevice = deviceRepo.save(device);
		
		//return String.valueOf(savedDevice.getId());
		return new ResponseEntity<Device>(savedDevice, HttpStatus.CREATED);
	}
	
	@GetMapping("/device/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		return ResponseEntity.ok(services.delete(id));
	}
}
