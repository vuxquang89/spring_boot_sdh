package vux.codejava.service;

import java.util.List;

import org.springframework.data.domain.Page;

import vux.codejava.entity.Category;
import vux.codejava.exception.CategoryNotFoundException;
import vux.codejava.response.CategoryResponse;

public interface CategoryServices {

	Page<Category> findAll(int pageNo, int pageSize);
	
	CategoryResponse findAllCategory(String keyword, int pageNo, int pageSize);
	
	List<Category> listAll();
	
	Category save(Category category);
	
	Category findById(long categoryId) throws CategoryNotFoundException;
	
	String delete(long categoryId) throws CategoryNotFoundException;
}
