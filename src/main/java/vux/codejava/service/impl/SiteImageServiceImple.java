package vux.codejava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.SiteImage;
import vux.codejava.repository.SiteImageRepository;
import vux.codejava.service.SiteImageServices;

@Service
public class SiteImageServiceImple implements SiteImageServices{

	@Autowired
	private SiteImageRepository repo;
	
	@Override
	public SiteImage save(SiteImage siteImage) {
		return repo.save(siteImage);
	}
	
	@Override
	public List<SiteImage> findAllSiteImage(Long siteInfoId) {
		return repo.findAllSiteImage(siteInfoId);
	}
	
	@Override
	public List<SiteImage> findSiteImageById(Long siteImageId) {
		return repo.findSiteImageById(siteImageId);
	}
	
	@Override
	public boolean exits(Long siteImageId) {
		return repo.existsById(siteImageId);
	}
	@Override
	public void delete(Long siteImageId) {
		repo.deleteById(siteImageId);
	}
}
