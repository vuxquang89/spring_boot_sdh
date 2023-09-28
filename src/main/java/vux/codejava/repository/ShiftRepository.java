package vux.codejava.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vux.codejava.entity.shift.ShiftEntity;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Long>{

	Optional<ShiftEntity> findByKeyCode(String keyCode);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM receive_shift WHERE Month(date_receive)=?1 && YEAR(date_receive)=?2 ORDER BY user_receive ASC", nativeQuery = true)
	List<ShiftEntity> findByDateReceive(String month, String year);
}
