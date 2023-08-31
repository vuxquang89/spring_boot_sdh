package vux.codejava.service.operate;

import java.util.List;

import vux.codejava.entity.operate.CableLink;
import vux.codejava.entity.operate.CableType;

public interface CableLinkServices {

	List<CableLink> findAll();
	CableLink getCableLinkById(Long cableLinkId);
	List<CableLink> findByDistrict(Long cableTypeId, String district);
	List<CableLink> findById(Long cableTypeId);
	List<CableLink> findCableLinkByCableType(CableType cableType);
}
