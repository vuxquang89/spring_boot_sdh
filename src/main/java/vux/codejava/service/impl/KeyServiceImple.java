package vux.codejava.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.shift.KeyEntity;
import vux.codejava.repository.KeyRepository;
import vux.codejava.service.KeyServices;

@Service
public class KeyServiceImple implements KeyServices{

	@Autowired
	private KeyRepository keyRepository;
	
	@Override
	public KeyEntity findByUsername(String username) {
		return keyRepository.findByUsername(username).get(0);
	}

	@Override
	public KeyEntity findByDistrict(String district) {
		return keyRepository.findByDistrict(district).get(0);
	}

	@Override
	public List<KeyEntity> findAll() {
		return keyRepository.findAll();
	}

	@Override
	public KeyEntity save(KeyEntity entity) {
		return keyRepository.save(entity);
	}

	@Override
	public Optional<KeyEntity> findByUsernameAndDistrict(String username, String district) {
		return keyRepository.findByUsernameAndDistrict(username, district);
	}
	
	
	
}
