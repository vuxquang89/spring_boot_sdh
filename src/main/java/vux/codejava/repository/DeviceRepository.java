package vux.codejava.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vux.codejava.entity.Category;
import vux.codejava.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

	List<Device> findByCategory(Category category);
	
	Device findDeviceById(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM devices d WHERE d.device_id "
			+ "NOT IN (SELECT s.device_id FROM sites_info s WHERE s.site_id = ?1) "
			+ "ORDER BY d.device_id ASC", nativeQuery = true)
	List<Device> findDeviceForSiteById(Long siteId);
	
	@Transactional
	//@Query(value = "SELECT DISTINCT c FROM categories c JOIN c.devices d "
	//		+ "WHERE c.id = ?2 AND (d.name LIKE %?1% OR c.name LIKE %?1%) ")
	@Query(value = "SELECT d FROM devices d JOIN categories c ON c.id = d.category.id "
			+ "WHERE c.id = ?2 AND (d.name LIKE %?1% OR c.name LIKE %?1%) "
			+ "ORDER BY d.id ASC")
	Page<Device> getAll(String keyword, Long categoryId, Pageable pageable);
}
