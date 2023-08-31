package vux.codejava;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import vux.codejava.entity.User;
import vux.codejava.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		/*
		User user = new User();
		user.setEmail("vu.dq@gmail.com");
		user.setUsername("vu.dq");
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "123456";
		String encoderPassword = encoder.encode(rawPassword);
		user.setPassword(encoderPassword);
		
		User savedUser = repo.save(user);
		
		User existUser = entityManager.find(User.class, savedUser.getId());
		
		
		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
		*/
	}
	
	@Test
	public void testFindUsername() {
		/*
		String username = "quangvu";
		User user = repo.findByUsername(username);
		assertThat(user).isNotNull();
		*/
	}
}
