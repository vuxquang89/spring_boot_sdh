package vux.codejava.service.impl.operate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.operate.ServiceInternal;
import vux.codejava.repository.operate.ServiceInternalRepository;
import vux.codejava.service.operate.ServiceInternalServices;

@Service
public class ServiceInternalImple implements ServiceInternalServices{

	@Autowired
	private ServiceInternalRepository repo;
	
	@Override
	public List<ServiceInternal> findAll() {
		return repo.findAll();
	}
}
