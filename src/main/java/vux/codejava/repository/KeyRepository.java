package vux.codejava.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vux.codejava.entity.shift.KeyEntity;

public interface KeyRepository extends JpaRepository<KeyEntity, Long>{

	public List<KeyEntity> findByUsername(String username);
	public List<KeyEntity> findByDistrict(String district);
	public Optional<KeyEntity> findByUsernameAndDistrict(String username, String district);
}
