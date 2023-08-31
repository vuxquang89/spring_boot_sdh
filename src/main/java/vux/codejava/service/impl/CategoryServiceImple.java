package vux.codejava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vux.codejava.entity.Category;
import vux.codejava.exception.CategoryNotFoundException;
import vux.codejava.repository.CategoryRepository;
import vux.codejava.response.CategoryResponse;
import vux.codejava.service.CategoryServices;

@Service
public class CategoryServiceImple implements CategoryServices{

	@Autowired
	private CategoryRepository repo;
	
	@Override
	public Page<Category> findAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return repo.findAll(pageable);
	}
	
	@Override
	public List<Category> listAll() {
		return repo.findAllByOrderByIdAsc();
	}
	
	@Override
	public CategoryResponse findAllCategory(String keyword, int pageNo, int pageSize) {
		//Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Category> page = repo.list(keyword, pageable);
		List<Category> content = page.getContent();
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(content);
		categoryResponse.setPageNo(page.getNumber());
		categoryResponse.setPageSize(page.getSize());
		categoryResponse.setTotalPages(page.getTotalPages());
		categoryResponse.setTotalItems(page.getTotalElements());
		return categoryResponse;
	}
	
	@Override
	public Category save(Category category) {
		if(category.getId() != null && repo.existsById(category.getId())) {
			
			repo.update(category.getName(), category.getId());
			return category;
			//return null;
		}else {
			
			return repo.save(category);
		}
	}
	
	@Override
	public Category findById(long categoryId) throws CategoryNotFoundException {
		Category category = repo.findCategoryById(categoryId);
		
		if(category != null) {
			return category;
		}else {
			throw new CategoryNotFoundException("Danh mục không được tìm thấy với id : " + categoryId);
		}
	}
	
	@Override
	public String delete(long categoryId) throws CategoryNotFoundException {
		Category category = findById(categoryId);		
		repo.delete(category);
		return "done";
		
	}
}
