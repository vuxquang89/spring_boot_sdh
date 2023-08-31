package vux.codejava.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vux.codejava.entity.Site;
import vux.codejava.entity.SiteInfo;

@Repository
public interface SiteInfoRepository extends JpaRepository<SiteInfo, Long> {

	Page<SiteInfo> getSiteInfoBySite(Site site, Pageable pageable);
	
	@Transactional	
	@Query(value = "SELECT s FROM sites_info s JOIN devices d ON d.id = s.device.id "
			+ "JOIN categories c ON c.id = d.category.id "
			+ "JOIN sites si ON si.id = s.site.id "
			+ "WHERE si.id = ?2 AND (d.name LIKE %?1% OR c.name LIKE %?1%) "
			+ "ORDER BY s.id ASC")
	Page<SiteInfo> list(String keyword, Long siteId, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM sites_info WHERE site_id = ?1", nativeQuery = true)
	List<SiteInfo> findSiteInfoBySiteId(Long siteId);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM sites_info WHERE site_info_id = ?1", nativeQuery = true)
	List<SiteInfo> findSiteInfoById(Long siteInfoId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM sites_info s WHERE s.site_id = ?1", nativeQuery = true)
	void deleteSiteInfoBySite(Long siteId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM sites_info s WHERE s.site_id IN "
			+ "(SELECT si.site_id FROM sites si INNER JOIN districts d "
			+ "WHERE si.district_id = d.district_id AND d.district_id = ?1)", nativeQuery = true)
	void deleteSiteInfoByDistrict(Long districtId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM sites_info s WHERE s.device_id = ?1", nativeQuery = true)
	void deleteSiteInfoByDevice(Long deviceId);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO sites_info(site_id, device_id) VALUES (?1, ?2)", nativeQuery = true)
	void saveSiteInfo(Long siteId, Long deviceId);
	/*
	@Modifying
	@Query("update Customer u set u.phone = :phone where u.id = :id")
	void updatePhone(@Param(value = "id") long id, @Param(value = "phone") String phone);
	*/
	@Transactional
	@Modifying
	@Query(value = "UPDATE sites_info SET quantity = ?1, serial_number = ?2, slot = ?3, note = ?4 WHERE site_info_id = ?5", nativeQuery = true)
	void updateSiteInfo(Integer quantity, String serialNumber, String slot, String note, Long siteInfoId);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM sites_info WHERE site_id = ?1 AND device_id = ?2", nativeQuery = true)
	List<SiteInfo> findSiteInfoById(Long siteId, Long deviceId);
	
	/*
	TypedQuery<SiteInfo> query 
    = entityManager.createQuery(
        "SELECT ph FROM Employee e JOIN e.phones ph WHERE ph LIKE '1%'", Phone.class);
	List<Phone> resultList = query.getResultList();
	*/
}
