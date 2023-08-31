package vux.codejava.service;

import java.util.List;

import vux.codejava.entity.Site;
import vux.codejava.entity.SiteInfo;
import vux.codejava.response.SiteInfoResponse;

public interface SiteInfoServices {

	SiteInfoResponse getSitesInfo(Site site, int pageNo, int pageSize);
	
	SiteInfoResponse getAllSitesInfo(String keyWord, Long siteId, int pageNo, int pageSize);
	
	List<SiteInfo> listSiteInfoBySiteId(Long siteId);
	
	SiteInfo savedSiteInfo(Long siteId, Long deviceId);
	
	void updateSiteInfo(Integer quantity, String serialNumber, String slot, String note, Long siteInfoId);
	
	SiteInfo save(SiteInfo siteInfo);
	
	List<SiteInfo> findSiteInfoById(Long siteInfoId);
	
	boolean exists(Long siteInfoId);
	
	String delete(Long siteInfoId);
}
