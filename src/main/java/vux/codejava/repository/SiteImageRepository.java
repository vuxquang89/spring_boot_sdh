package vux.codejava.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vux.codejava.entity.SiteImage;

public interface SiteImageRepository extends JpaRepository<SiteImage, Long>{

	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM site_images WHERE site_info_id = ?1", nativeQuery = true)
	List<SiteImage> findAllSiteImage(Long siteInfoId);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM site_images WHERE site_image_id = ?1", nativeQuery = true)
	List<SiteImage> findSiteImageById(Long siteImageId);
}
