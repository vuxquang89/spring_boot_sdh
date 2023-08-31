package vux.codejava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.District;
import vux.codejava.entity.Site;
import vux.codejava.exception.SiteNotFoundException;
import vux.codejava.repository.SiteInfoRepository;
import vux.codejava.repository.SiteRepository;
import vux.codejava.response.SiteResponse;
import vux.codejava.service.SiteServices;

@Service
public class SiteServiceImple implements SiteServices{

	@Autowired
	private SiteRepository siteRepo;
	
	@Autowired
	private SiteInfoRepository siteInfoRepo;
	
	@Override
	public Site findSiteById(Long siteId) throws SiteNotFoundException{
		Site site = siteRepo.findSiteById(siteId);
		if(site != null) {
			return site;
		}else {
			throw new SiteNotFoundException("Không tìm thấy site với id : " + siteId);
		} 
	}
	@Override
	public Long findSite(String siteName, Long districtId) {
		return siteRepo.findSite(siteName, districtId);
	}
	
	@Override
	public List<Site> findAllByDistrict(District district) {
		return siteRepo.findAllByDistrict(district);
	}
	
	@Override
	public List<Site> findAllByDistrict(Long districtId) {
		return siteRepo.findAllByDistrict(districtId);
	}
	
	@Override
	public String delete(Long districtId) throws SiteNotFoundException {
		Site site = findSiteById(districtId);
		siteInfoRepo.deleteSiteInfoBySite(districtId);
		siteRepo.delete(site);
		return "done";
	}
	
	@Override
	public SiteResponse save(Site site) {
		SiteResponse siteResponse = new SiteResponse();
		if(site.getId() != null && siteRepo.existsById(site.getId())) {
			//update
			System.out.println("update");
			siteRepo.update(site.getName(), site.getId()); 
			siteResponse.setStatus(200);
			siteResponse.setMessage("Cập nhật thành công");
			siteResponse.setSite(site);
			return siteResponse;
		}
		Long countSite = findSite(site.getName(), site.getDistrict().getId());
		
		if(countSite > 0) {
			System.out.println("error");
			siteResponse.setStatus(400);
			siteResponse.setMessage("Kiểm tra dữ liệu đã tồn tại");
			siteResponse.setError("Kiểm tra dữ liệu đã tồn tại");
		}else {
			System.out.println("save");
			siteResponse.setStatus(200);
			siteResponse.setMessage("Thêm mới thành công");
			siteResponse.setSite(siteRepo.save(site));			
		}
		return siteResponse;
	}
	@Override
	public boolean existsById(Long siteId) {
		return siteRepo.existsById(siteId);
	}
}
