package vux.codejava.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vux.codejava.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT u FROM categories u")
	List<Category> findCatagories();
	
	public List<Category> findAllByOrderByIdAsc();
	
	@Transactional
	@Query(value = "SELECT * FROM categories "
			+ "WHERE category_name LIKE %?1% ORDER BY category_id ASC", 
			nativeQuery = true)
	Page<Category> list(String keyword, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE categories SET category_name = ?1 WHERE category_id = ?2", nativeQuery = true)
	void update(String categoryName, Long categoryId);
	
	Category findCategoryById(Long categoryId);
}
