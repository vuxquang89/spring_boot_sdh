package vux.codejava.service.impl.operate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.operate.Status;
import vux.codejava.repository.operate.StatusRepository;
import vux.codejava.service.operate.StatusServices;

@Service
public class StatusImple implements StatusServices{
	@Autowired
	private StatusRepository repo;
	
	@Override
	public List<Status> findAll() {
		return repo.findAll();
	}
}
