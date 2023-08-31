package vux.codejava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import vux.codejava.entity.Device;
import vux.codejava.entity.Site;
import vux.codejava.entity.SiteInfo;
import vux.codejava.repository.SiteInfoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SiteInfoRepositoryTests {

	@Autowired
	private SiteInfoRepository siteInfoRepo;
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateSiteInfo() {
		/*
		Site site = entityManager.find(Site.class, 35L);
		Device device = entityManager.find(Device.class, 11L);
		
		SiteInfo siteInfo = new SiteInfo(site, device);
		//siteInfoRepo.saveSiteInfo(site.getId(), device.getId());
		SiteInfo save = siteInfoRepo.save(siteInfo);
		
		assertTrue(save.getId() > 0);
		*/
	}
	
	@Test
	public void testGetSiteInfoById() {
		/*
		Site site = entityManager.find(Site.class, 33L);
		Device device = entityManager.find(Device.class, 11L);
		List<SiteInfo> listSiteInfo = siteInfoRepo.findSiteInfoById(site.getId(), device.getId());
		
		for(SiteInfo siteInfo : listSiteInfo) {
			System.out.println(siteInfo.getId());
		}
		*/
	}
	
	@Test
	public void testGetSiteInfoBySiteId() {
		/*
		Site site = entityManager.find(Site.class, 58L);
		
		List<SiteInfo> listSiteInfo = siteInfoRepo.findSiteInfoBySiteId(site.getId());
		
		for(SiteInfo siteInfo : listSiteInfo) {
			System.out.println(siteInfo.getSite().getId());
		}
		*/
	}     
}
