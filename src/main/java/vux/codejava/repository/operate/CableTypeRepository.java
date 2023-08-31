package vux.codejava.repository.operate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vux.codejava.entity.operate.CableType;

public interface CableTypeRepository extends JpaRepository<CableType, Long>{

	@Query("SELECT c FROM cable_type c WHERE c.id = ?1")
	CableType findCableTypeById(Long cableTypeId);
	
	@Query("SELECT ct FROM cable_link cl JOIN cable_type ct ON ct.id = cl.cableType.id "
			+ "WHERE cl.id = ?1")
	CableType findCableTypeByCableLinkId(Long cableLinkId);
}
