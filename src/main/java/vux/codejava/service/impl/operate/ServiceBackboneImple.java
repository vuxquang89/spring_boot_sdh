package vux.codejava.service.impl.operate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.operate.ServiceBackbone;
import vux.codejava.repository.operate.ServiceBackboneRepository;
import vux.codejava.service.operate.ServiceBackboneServices;

@Service
public class ServiceBackboneImple implements ServiceBackboneServices{

	@Autowired
	private ServiceBackboneRepository repo;
	
	@Override
	public List<ServiceBackbone> findAll() {
		return repo.findAll();
	}
}
