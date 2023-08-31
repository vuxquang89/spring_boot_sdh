package vux.codejava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.District;
import vux.codejava.exception.DistrictNotFoundException;
import vux.codejava.repository.DistrictRepository;
import vux.codejava.repository.SiteInfoRepository;
import vux.codejava.service.DistrictServices;

@Service
public class DistrictServiceImple implements DistrictServices{

	@Autowired
	private DistrictRepository repo;
	
	@Autowired
	private SiteInfoRepository siteInfoRepo;
	
	@Override
	public List<District> findAll() {
		return repo.findAll();
	}
	
	@Override
	public District save(District district) {
		
		if(district.getId() != null && repo.existsById(district.getId())) {
			
			repo.updateDistrict(district.getName(), district.getId());
			return district;
		}else {
			
			return repo.save(district);
		}
		
	}
	
	@Override
	public District findDistrictById(Long districtId) throws DistrictNotFoundException {
		District district = repo.findDistrictById(districtId);
		if(district != null) {
			return district;
		}else {
			throw new DistrictNotFoundException("Khu vực không được tìm thấy với id : " + districtId);
		}
		
	}
	
	@Override
	public List<District> listDistrictById(Long districtId) {
		return repo.listDistrictById(districtId);
	}
	
	@Override
	public String delete(Long districtId) throws DistrictNotFoundException {
		District district = findDistrictById(districtId);
		siteInfoRepo.deleteSiteInfoByDistrict(district.getId());
		repo.delete(district);
		return "done";
	}
}
