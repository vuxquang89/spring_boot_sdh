package vux.codejava;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import vux.codejava.entity.Category;
import vux.codejava.entity.Device;
import vux.codejava.repository.DeviceRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DeviceRepositoryTests {
	
	@Autowired
	private DeviceRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateDevice() {
		/*
		Category category = entityManager.persist(new Category("Tu nguon chinh"));
		Device saveDevice = repo.save(new Device("UPS", category));
		
		assertNotNull(saveDevice);
		assertTrue(saveDevice.getId() > 0);
		*/
	}
	
	@Test
	public void testGetDevicesForSite() {
		/*
		List<Device> listDevice = repo.findDeviceForSiteById(51L);
		
		for(Device d : listDevice) {
			System.out.println(d.getId());
		}
		*/
	}

}
