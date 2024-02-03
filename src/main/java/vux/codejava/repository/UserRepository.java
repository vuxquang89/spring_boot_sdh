package vux.codejava.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vux.codejava.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM users u where u.username = ?1")
	User findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query(value="SELECT * FROM users WHERE role = 3 AND enabled = 1", nativeQuery = true)
	List<User> findAllUser();

	public  Optional<User> findByEmail(String email);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE users SET password = ?1 WHERE username = ?2", nativeQuery = true)
	void updateUser(String password, String username);
}
