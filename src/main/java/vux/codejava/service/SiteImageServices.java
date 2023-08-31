package vux.codejava.service;

import java.util.List;

import vux.codejava.entity.SiteImage;

public interface SiteImageServices {
	
	SiteImage save(SiteImage siteImage);
	List<SiteImage> findAllSiteImage(Long siteInfoId);
	List<SiteImage> findSiteImageById(Long siteImageId);
	boolean exits(Long siteImageId);
	void delete(Long siteImageId);
}
