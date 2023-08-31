package vux.codejava.repository.operate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vux.codejava.entity.operate.CableLink;
import vux.codejava.entity.operate.CableType;

public interface CableLinkRepository extends JpaRepository<CableLink, Long>{

	@Query("SELECT c FROM cable_link c WHERE c.cableType.id = ?1 AND c.district = ?2")
	List<CableLink> findCableLinkByIdAndDistrict(Long cabelTypeId, String district);
	
	@Query("SELECT c FROM cable_link c WHERE c.cableType.id = ?1")
	List<CableLink> findCableLinkById(Long cabelTypeId);
	
	@Query("SELECT c FROM cable_link c WHERE c.id = ?1")
	CableLink getCableLinkById(Long cableLinkId);
	
	@Query("SELECT c FROM cable_link c WHERE c.cableType = :cableType")
	List<CableLink> findCableLinkByCableType(@Param("cableType") CableType cableType);
}
