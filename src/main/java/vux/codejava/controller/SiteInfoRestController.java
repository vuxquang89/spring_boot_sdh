package vux.codejava.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vux.codejava.entity.CustomSiteInfo;
import vux.codejava.entity.Device;
import vux.codejava.entity.DevicesOfSite;
import vux.codejava.entity.MultipartFiles;
import vux.codejava.entity.Site;
import vux.codejava.entity.SiteImage;
import vux.codejava.entity.SiteInfo;
import vux.codejava.exception.BaseException;
import vux.codejava.exception.SiteNotFoundException;
import vux.codejava.lib.Thumbnail;
import vux.codejava.response.SiteInfoResponse;
import vux.codejava.response.SiteInfoResponseEntity;
import vux.codejava.service.DeviceServices;
import vux.codejava.service.SiteImageServices;
import vux.codejava.service.SiteInfoServices;
import vux.codejava.service.SiteServices;
import vux.codejava.util.AppConstants;
import vux.codejava.util.FileAction;
import vux.codejava.util.FileUploadUtil;

@RestController
@RequestMapping("/api")
public class SiteInfoRestController {
	private final String OPT_FOLDER = "images";
	private final String UPLOAD_FOLDER = "upload";
	
	@Autowired
	private SiteInfoServices siteInfoServices;
	
	@Autowired
	private DeviceServices deviceServices;
	
	@Autowired
	private SiteServices siteServices;
	
	@Autowired
	private SiteImageServices siteImageServices;

	@PostMapping("/siteinfo/device/save")
	public String save(@RequestBody DevicesOfSite devicesOfSite) {
		
		int countDevices = devicesOfSite.getDevicesId().length;
		Long siteId = devicesOfSite.getSiteId();
		
		int count = 0;
		if(siteServices.existsById(siteId)) {
			for(Long deviceId : devicesOfSite.getDevicesId()) {			
				
				if(deviceServices.existsById(deviceId)) {
					siteInfoServices.savedSiteInfo(siteId, deviceId);
					count++;
				}
			
			}
		}
		
		if(count == countDevices)
			return String.valueOf("OK");
		
		return String.valueOf("NO");
	}
	
	@GetMapping("/siteinfo/device/delete/{id}")
	public String delete(@PathVariable("id") Long siteInfoId) {
		if(siteInfoServices.exists(siteInfoId)) {
			List<SiteImage> listSiteImages = siteImageServices.findAllSiteImage(siteInfoId);
			for(SiteImage siteImage : listSiteImages) {
				FileAction.delete(siteImage.getPathName(), siteImage.getFileName());
				FileAction.delete(siteImage.getPathName(), siteImage.getFileName100());
			}
			siteInfoServices.delete(siteInfoId);
			return String.valueOf("OK");
		}
		return String.valueOf("NO");
	}
	
	@GetMapping("/siteinfo/image/device/delete/{id}")
	public String deleteSiteImage(@PathVariable("id") Long siteImageId) {
		if(siteImageServices.exits(siteImageId)) {
			List<SiteImage> listSiteImages = siteImageServices.findSiteImageById(siteImageId);
			for(SiteImage siteImage : listSiteImages) {
				FileAction.delete(siteImage.getPathName(), siteImage.getFileName());
				FileAction.delete(siteImage.getPathName(), siteImage.getFileName100());
			}
			siteImageServices.delete(siteImageId);
			return String.valueOf("OK");
		}
		return String.valueOf("NO");
	}
	
	@GetMapping("/siteinfo/{id}")
	public List<SiteInfo> loadSiteInfo(@PathVariable("id") Long siteInfoId) {
		if(siteInfoServices.exists(siteInfoId)) {
			return siteInfoServices.findSiteInfoById(siteInfoId);
		}
		return null;
	}
	
	@GetMapping("/siteinfo/{id}/page")
	public ResponseEntity<SiteInfoResponse> getSiteInfo(
			@PathVariable("id") Long siteId,
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_PAGE_SEARCH, required = false) String keyWord,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {
		//Site site = siteRepo.findSiteById(siteId);
		//return siteInfoServices.getSitesInfo(site, pageNo, pageSize);
		return ResponseEntity.ok(siteInfoServices.getAllSitesInfo(keyWord, siteId, pageNo, pageSize));
	}
	
	@PostMapping("/siteinfo/save")
	public ResponseEntity<SiteInfoResponseEntity> saveSiteInfo(@ModelAttribute CustomSiteInfo customSiteInfo) throws SiteNotFoundException{
		System.out.println("save SiteInfo REST API...");
		System.out.println(customSiteInfo.getDeviceId());
		
		Long siteId = customSiteInfo.getSiteId();
		Long deviceId = customSiteInfo.getDeviceId();
		Integer quantity = customSiteInfo.getQuantity();
		String serial = customSiteInfo.getSerialNumber();
		String slot = customSiteInfo.getSlot();
		String note = customSiteInfo.getNote();
		SiteInfoResponseEntity siteInfoResponseEntity = new SiteInfoResponseEntity();
		
		if(quantity == null || quantity < 1) {
			siteInfoResponseEntity.setStatus(401);
			siteInfoResponseEntity.setMessage("Sỗ lượng phải lớn hơn 0");
			siteInfoResponseEntity.setError("Lỗi thêm số lượng");
			return ResponseEntity.ok(siteInfoResponseEntity);
		}
		
		if(siteServices.existsById(siteId) && deviceServices.existsById(deviceId)) {
			
			Site site = siteServices.findSiteById(siteId);
			Device device = deviceServices.findDeviceById(deviceId);
			
			SiteInfo siteInfo = new SiteInfo();
			siteInfo.setSite(site);
			siteInfo.setDevice(device);
			siteInfo.setQuantity(quantity);
			siteInfo.setSerialNumber(serial);
			siteInfo.setSlot(slot);
			siteInfo.setNote(note);
			
			SiteInfo saveSiteInfo = siteInfoServices.save(siteInfo);
			
			if(saveSiteInfo != null) {
				siteInfoResponseEntity.setStatus(200);
				siteInfoResponseEntity.setMessage("Thêm mới thành công");
				siteInfoResponseEntity.setContent(saveSiteInfo);
				try {
					
					if(customSiteInfo.getFiles() != null && customSiteInfo.getFiles().length > 0) {
						String path = OPT_FOLDER + "/" + customSiteInfo.getDistrictId() + "/" + customSiteInfo.getSiteId();
						List<String> fileNames = saveUploadedFiles(Arrays.asList(customSiteInfo.getFiles()), path);
						Set<SiteImage> images = new HashSet<SiteImage>();
						for(String name : fileNames) {
							String fileNameResize = "";
							try {
								fileNameResize = Thumbnail.resize_100(UPLOAD_FOLDER+"/"+path, name);
								
							} catch (BaseException e) {								
								e.printStackTrace();
							}
							SiteImage siteImage = siteImageServices.save(new SiteImage(name, fileNameResize, UPLOAD_FOLDER+"/"+path, saveSiteInfo));
							images.add(siteImage);
						}
						siteInfoResponseEntity.getContent().setSiteImages(images);
					}
				} catch (IOException e) {
					siteInfoResponseEntity.setStatus(400);
					siteInfoResponseEntity.setMessage("Thêm mới không thành công");
					siteInfoResponseEntity.setError("Lỗi thêm mới hình ảnh");
					e.printStackTrace();
				}
			}else {
				siteInfoResponseEntity.setStatus(400);
				siteInfoResponseEntity.setMessage("Thêm mới không thành công");
				siteInfoResponseEntity.setError("Lỗi thêm mới");
			}
			
		}
		
		
		return new ResponseEntity<SiteInfoResponseEntity>(siteInfoResponseEntity, HttpStatus.CREATED);
	}
	
	@PostMapping("/siteinfo/update")
	public ResponseEntity<SiteInfoResponseEntity> updateSiteInfo(@ModelAttribute SiteInfo siteInfo, 
			@ModelAttribute MultipartFiles files,
			@RequestParam("districtId") Long districtId,
			@RequestParam("siteId") Long siteId,
			@RequestParam("deviceId") Long deviceId,
			@RequestParam("deviceName") String deviceName){
		System.out.println("update SiteInfo REST API...");
		System.out.println(siteInfo.getId());
		
		Long siteInfoId = siteInfo.getId();
		
		Integer quantity = siteInfo.getQuantity();
		String serial = siteInfo.getSerialNumber();
		String slot = siteInfo.getSlot();
		String note = siteInfo.getNote();
		SiteInfoResponseEntity siteInfoResponseEntity = new SiteInfoResponseEntity();
		
		if(quantity == null || quantity < 1) {
			siteInfoResponseEntity.setStatus(401);
			siteInfoResponseEntity.setMessage("Sỗ lượng phải lớn hơn 0");
			siteInfoResponseEntity.setError("Lỗi thêm số lượng");
			return ResponseEntity.ok(siteInfoResponseEntity);
		}
		
		if(siteInfoServices.exists(siteInfoId)) {
			siteInfoServices.updateSiteInfo(quantity, serial, slot, note, siteInfoId);
			siteInfoResponseEntity.setStatus(200);
			siteInfoResponseEntity.setMessage("Cập nhật thành công");
			Device device = new Device(deviceId, deviceName);
			siteInfo.setDevice(device);
			siteInfoResponseEntity.setContent(siteInfo);
			
			try {
				if(files.getFiles() != null && files.getFiles().length > 0) {
					String path = OPT_FOLDER + "/" + districtId + "/" + siteId;
					List<String> fileNames = saveUploadedFiles(Arrays.asList(files.getFiles()), path);
					
					for(String name : fileNames) {
						String fileNameResize = "";
						try {
							fileNameResize = Thumbnail.resize_100(UPLOAD_FOLDER+"/"+path, name);
							
						} catch (BaseException e) {								
							e.printStackTrace();
						}
						siteImageServices.save(new SiteImage(name, fileNameResize, UPLOAD_FOLDER+"/"+path, siteInfo));
						
					}
					
				}
				
				Set<SiteImage> images = new HashSet<SiteImage>();
				List<SiteImage> listSiteImages = siteImageServices.findAllSiteImage(siteInfoId);
				for(SiteImage siteImage : listSiteImages) {
					images.add(siteImage);
				}
				siteInfoResponseEntity.getContent().setSiteImages(images);
			} catch (IOException e) {
				siteInfoResponseEntity.setStatus(400);
				siteInfoResponseEntity.setMessage("Cập nhật thất bại");
				siteInfoResponseEntity.setError("Lỗi thêm mới hình ảnh");
				e.printStackTrace();
			}
			
		}else {
			siteInfoResponseEntity.setStatus(400);
			siteInfoResponseEntity.setMessage("Cập nhật thất bại");
			siteInfoResponseEntity.setError("Không tồn tại id : " + siteId);
		}
		
		return ResponseEntity.ok(siteInfoResponseEntity);
	}
	
	@GetMapping("/siteinfo/image/list/{id}")
	public ResponseEntity<List<SiteImage>> listSiteImage(@PathVariable("id") Long siteInfoId){
		System.out.println("get list Site Image REST API...");
		System.out.println(siteInfoId);
		return ResponseEntity.ok(siteImageServices.findAllSiteImage(siteInfoId));
	}
	
	@GetMapping("/siteinfo/list/{id}")
	public List<SiteInfo> listSiteInfo(@PathVariable("id") Long siteId){
		
		//Site site = siteServices.findSiteById(siteId);
		//return siteInfoServices.listSitesInfo(site);
		return siteInfoServices.listSiteInfoBySiteId(siteId);
	}
	
	//save file
    private List<String> saveUploadedFiles(List<MultipartFile> files, String pathUpload) throws IOException {
    	List<String> fileNames = new ArrayList<String>();
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            fileName = FileUploadUtil.saveFile(pathUpload, fileName, file);
            System.out.println(fileName);
            fileNames.add(fileName);
        }
        return fileNames;
    }
}
