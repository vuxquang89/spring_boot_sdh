package vux.codejava;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import vux.codejava.entity.User;
import vux.codejava.entity.operate.CableLink;
import vux.codejava.entity.operate.Event;
import vux.codejava.entity.operate.OperationalStatistics;
import vux.codejava.entity.operate.ServiceBackbone;
import vux.codejava.entity.operate.ServiceCustomer;
import vux.codejava.entity.operate.ServiceInternal;
import vux.codejava.entity.operate.Status;
import vux.codejava.repository.operate.OperationalStatisticsRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OperationalStatisticsTest {

	@Autowired
	private OperationalStatisticsRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	private void createOperational() {
		User user = entityManager.find(User.class, 1L);
		CableLink cableLink = entityManager.find(CableLink.class, 1L);
		Event event = entityManager.find(Event.class, 1L);
		LocalDateTime startTime = LocalDateTime.parse("2022-12-28 01:00:00");
		LocalDateTime endTime = LocalDateTime.parse("2022-12-27 05:00:00");
		Status status = entityManager.find(Status.class, 1L);
		String note = "BD MX 119...";
		LocalDateTime createTime = LocalDateTime.now();
		LocalDateTime updateTime = createTime;
		int processingTime = 4;
		ServiceInternal serviceInternal = entityManager.find(ServiceInternal.class, 1L);
		ServiceCustomer serviceCustomer = entityManager.find(ServiceCustomer.class, 1L);
		ServiceBackbone serviceBackbone = entityManager.find(ServiceBackbone.class, 1L);
		OperationalStatistics oper = repo.save(new OperationalStatistics(user.getUsername(), cableLink, event, startTime, endTime, processingTime, 
				status, note, createTime, updateTime, true, serviceInternal, serviceCustomer, serviceBackbone));
		System.out.println(oper.getId());
		assertNotNull(oper);
		assertTrue(oper.getId() > 0);
	}
}
