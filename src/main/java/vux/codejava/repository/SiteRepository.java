package vux.codejava.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vux.codejava.entity.District;
import vux.codejava.entity.Site;

public interface SiteRepository extends JpaRepository<Site, Long> {

	List<Site> findAllByDistrict(District district);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM sites WHERE district_id = ?1", nativeQuery = true)
	List<Site> findAllByDistrict(Long districtId);
	
	@Transactional
	@Query(value = "SELECT count(*) FROM sites s INNER JOIN districts d "
			+ "ON s.district_id = d.district_id "
			+ "WHERE d.district_id = ?2 AND s.site_name LIKE ?1", nativeQuery = true)
	Long findSite(String siteName, Long districtId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE sites SET site_name = ?1 WHERE site_id = ?2", nativeQuery = true)
	void update(String siteName, Long siteId);
	
	Site findSiteById(Long siteId);
}
