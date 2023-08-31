package vux.codejava.service.impl.operate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.operate.ServiceCustomer;
import vux.codejava.repository.operate.ServiceCustomerRepository;
import vux.codejava.service.operate.ServiceCustomerServices;

@Service
public class ServiceCustomerImple implements ServiceCustomerServices{

	@Autowired
	private ServiceCustomerRepository repo;
	
	@Override
	public List<ServiceCustomer> findAll() {
		return repo.findAll();
	}
}
