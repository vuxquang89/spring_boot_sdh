package vux.codejava;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import vux.codejava.entity.District;
import vux.codejava.repository.DistrictRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DistrictRepositoryTests {

	@Autowired
	private DistrictRepository repo;
	@Test
	public void listDistrict() {
		/*
		List<District> listDistrict = repo.findAll();
		
		for(District district : listDistrict) {
			System.out.println(district.getName());
		}
		*/
	}
	
	@Test
	public void testFindDistrictById() {
		/*
		List<District> listDistrict = repo.listDistrictById(6L);
		for(District district : listDistrict) {
			System.out.println(district.getName());
		}
		*/
	}
}
