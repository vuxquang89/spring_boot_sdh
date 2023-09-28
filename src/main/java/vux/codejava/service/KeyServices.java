package vux.codejava.service;

import java.util.List;
import java.util.Optional;

import vux.codejava.entity.shift.KeyEntity;

public interface KeyServices {

	KeyEntity findByUsername(String username);
	KeyEntity findByDistrict(String district);
	Optional<KeyEntity> findByUsernameAndDistrict(String username, String district);
	KeyEntity save(KeyEntity entity);
	List<KeyEntity> findAll();
}
