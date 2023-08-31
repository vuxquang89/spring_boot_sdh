package vux.codejava.service;

import java.util.List;

import vux.codejava.entity.District;
import vux.codejava.exception.DistrictNotFoundException;

public interface DistrictServices {

	List<District> findAll();
	
	District save(District district);
	
	List<District> listDistrictById(Long districtId);
	
	District findDistrictById(Long districtId) throws DistrictNotFoundException;
	
	String delete(Long districtId) throws DistrictNotFoundException;
}
