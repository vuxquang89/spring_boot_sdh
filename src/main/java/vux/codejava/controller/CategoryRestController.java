package vux.codejava.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vux.codejava.entity.Category;
import vux.codejava.exception.CategoryNotFoundException;
import vux.codejava.repository.CategoryRepository;
import vux.codejava.response.CategoryResponse;
import vux.codejava.service.CategoryServices;
import vux.codejava.util.AppConstants;

@RestController
@RequestMapping("/api")
public class CategoryRestController {

	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private CategoryServices cateServices;
	
	@GetMapping("/category/list")
	public ResponseEntity<List<Category>> listAll(){
		System.out.println("listAll category REST API ...");
		//return cateRepo.findAll();
		return ResponseEntity.ok(cateRepo.findAll());
	}
	
	@GetMapping("/category/page")
	public ResponseEntity<CategoryResponse> categoryPage(Model model,
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_PAGE_SEARCH, required = false) String keyWord,
			@RequestParam(value = "currentPage", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int currentPage,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {
		
		//return cateServices.findAllCategory(keyWord, currentPage - 1, pageSize);
		return ResponseEntity.ok(cateServices.findAllCategory(keyWord, currentPage - 1, pageSize));
	}
	
	@PostMapping("/category/save")
	public ResponseEntity<Category> save(@RequestBody @Valid Category category) {
		System.out.println("save Category REST API....");
		System.out.println(category.getName());
		
		Category saveCategory = cateServices.save(category);
		
		//return String.valueOf(saveCategory.getId());
		return new ResponseEntity<Category>(saveCategory, HttpStatus.CREATED);
	}
	
	@GetMapping("/category/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws CategoryNotFoundException {
		return ResponseEntity.ok(cateServices.delete(id));
	}
	
}
