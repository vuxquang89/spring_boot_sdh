package vux.codejava;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import vux.codejava.entity.SiteImage;
import vux.codejava.repository.SiteImageRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SiteImageRepositoryTests {

	@Autowired
	private SiteImageRepository repo;
	
	@Test
	public void findAllSiteImage() {
		/*
		List<SiteImage> listSiteImage = repo.findAllSiteImage(33L);
		System.out.println(listSiteImage.size());
		for(SiteImage s : listSiteImage) {
			System.out.println(s.getFileName());
		}
		*/
	}
}
