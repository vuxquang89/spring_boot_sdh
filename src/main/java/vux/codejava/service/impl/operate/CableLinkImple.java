package vux.codejava.service.impl.operate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.operate.CableLink;
import vux.codejava.entity.operate.CableType;
import vux.codejava.repository.operate.CableLinkRepository;
import vux.codejava.service.operate.CableLinkServices;

@Service
public class CableLinkImple implements CableLinkServices{

	@Autowired
	private CableLinkRepository repo;
	
	@Override
	public List<CableLink> findAll() {
		return repo.findAll();
	}
	
	@Override
	public List<CableLink> findByDistrict(Long cableTypeId, String district) {
		return repo.findCableLinkByIdAndDistrict(cableTypeId, district);
	}
	
	@Override
	public List<CableLink> findById(Long cableTypeId) {
		return repo.findCableLinkById(cableTypeId);
	}
	@Override
	public List<CableLink> findCableLinkByCableType(CableType cableType) {
		return repo.findCableLinkByCableType(cableType);
	}
	
	@Override
	public CableLink getCableLinkById(Long cableLinkId) {
		return repo.getCableLinkById(cableLinkId);
	}
}
