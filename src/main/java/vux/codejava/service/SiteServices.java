package vux.codejava.service;

import java.util.List;

import vux.codejava.entity.District;
import vux.codejava.entity.Site;
import vux.codejava.exception.SiteNotFoundException;
import vux.codejava.response.SiteResponse;

public interface SiteServices {

	Site findSiteById(Long siteId) throws SiteNotFoundException;
	
	Long findSite(String siteName, Long districtId);
	
	boolean existsById(Long siteId);	
	SiteResponse save(Site site);
	
	List<Site> findAllByDistrict(District district);
	List<Site> findAllByDistrict(Long districtId);
	String delete(Long districtId) throws SiteNotFoundException;
}
