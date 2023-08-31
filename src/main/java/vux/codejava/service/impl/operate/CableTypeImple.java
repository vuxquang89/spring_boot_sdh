package vux.codejava.service.impl.operate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.operate.CableType;
import vux.codejava.repository.operate.CableTypeRepository;
import vux.codejava.service.operate.CableTypeServices;

@Service
public class CableTypeImple implements CableTypeServices{

	@Autowired
	private CableTypeRepository repo;
	
	@Override
	public List<CableType> findAll() {
		return repo.findAll(); 
	}
	
	@Override
	public CableType findCableTypeById(Long id) {
		return repo.findCableTypeById(id);
	}
}
