package vux.codejava.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vux.codejava.entity.District;

public interface DistrictRepository extends JpaRepository<District, Long>{

	District findDistrictById(Long districtId);
	
	@Query("SELECT u FROM districts u")
	List<District> getDistricts();
	
	@Query("SELECT d FROM districts e INNER JOIN e.sites d")
	List<District> listDistrist();
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM districts WHERE district_id = ?1", nativeQuery = true)
	List<District> listDistrictById(Long districtId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE districts SET district_name = ?1 WHERE district_id = ?2", nativeQuery = true)
	void updateDistrict(String dsitrictName, Long districtId);
}
