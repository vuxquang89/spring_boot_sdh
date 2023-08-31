package vux.codejava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vux.codejava.entity.Site;
import vux.codejava.entity.SiteInfo;
import vux.codejava.repository.SiteInfoRepository;
import vux.codejava.response.SiteInfoResponse;
import vux.codejava.service.SiteInfoServices;

@Service
public class SiteInfoServiceImple implements SiteInfoServices{

	@Autowired
	private SiteInfoRepository siteInfoRepo;
	
	@Override
	public SiteInfoResponse getSitesInfo(Site site, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Page<SiteInfo> pages = siteInfoRepo.getSiteInfoBySite(site, pageable);
		
		List<SiteInfo> listSiteInfo = pages.getContent();
		SiteInfoResponse siteInfoResponse = new SiteInfoResponse();
		siteInfoResponse.setContent(listSiteInfo);
		siteInfoResponse.setPageNo(pages.getNumber());
		siteInfoResponse.setPageSize(pages.getSize());
		siteInfoResponse.setTotalItems(pages.getTotalElements());
		siteInfoResponse.setTotalPages(pages.getTotalPages());
		return siteInfoResponse;
	}
	
	@Override
	public SiteInfoResponse getAllSitesInfo(String keyWord, Long siteId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		//Sort sort = Sort.by(Sort.Direction.ASC, "name");
		Page<SiteInfo> pages = siteInfoRepo.list(keyWord, siteId, pageable);
		
		List<SiteInfo> listSiteInfo = pages.getContent();
		SiteInfoResponse siteInfoResponse = new SiteInfoResponse();
		siteInfoResponse.setContent(listSiteInfo);
		siteInfoResponse.setPageNo(pages.getNumber());
		siteInfoResponse.setPageSize(pages.getSize());
		siteInfoResponse.setTotalItems(pages.getTotalElements());
		siteInfoResponse.setTotalPages(pages.getTotalPages());
		return siteInfoResponse;
	}
	
	@Override
	public List<SiteInfo> listSiteInfoBySiteId(Long siteId) {
		return siteInfoRepo.findSiteInfoBySiteId(siteId);
	}
	@Override
	public List<SiteInfo> findSiteInfoById(Long siteInfoId) {
		return siteInfoRepo.findSiteInfoById(siteInfoId);
		
	}
	@Override
	public SiteInfo savedSiteInfo(Long siteId, Long deviceId) {
		siteInfoRepo.saveSiteInfo(siteId, deviceId);
		return null;
	}
	@Override
	public void updateSiteInfo(Integer quantity, String serialNumber, String slot, String note, Long siteInfoId) {
		siteInfoRepo.updateSiteInfo(quantity, serialNumber, slot, note, siteInfoId);
	}
	
	@Override
	public SiteInfo save(SiteInfo siteInfo) {
		return siteInfoRepo.save(siteInfo);
	}
	@Override
	public boolean exists(Long siteInfoId) {
		return siteInfoRepo.existsById(siteInfoId);
	}
	@Override
	public String delete(Long siteInfoId) {
		siteInfoRepo.deleteById(siteInfoId);
		return "done";
	}
}
