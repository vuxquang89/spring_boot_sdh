package vux.codejava.service.operate;

import java.util.List;

import vux.codejava.entity.operate.CableType;

public interface CableTypeServices {

	List<CableType> findAll();
	
	CableType findCableTypeById(Long id);
}
