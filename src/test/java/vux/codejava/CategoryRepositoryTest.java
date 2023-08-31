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
import vux.codejava.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateCategory() {
		/*
		Category category = repo.save(new Category("Tu nguon"));
		
		assertNotNull(category);
		assertTrue(category.getId() > 0);
		*/
	}
	
	@Test
	public void testList() {
		/*
		entityManager.persist(new Category("May do"));
		entityManager.persist(new Category("May han"));
		
		List<Category> listCategorys = repo.findAll();
		for(Category cate : listCategorys) {
			System.out.println(cate.getName());
		}
		
		assertNotNull(listCategorys);
		*/
	}
}
