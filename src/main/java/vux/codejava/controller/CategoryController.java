package vux.codejava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vux.codejava.entity.Category;
import vux.codejava.repository.CategoryRepository;
import vux.codejava.service.CategoryServices;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private CategoryRepository cateRepo;
	@Autowired
	private CategoryServices categoryServices;
	
	@GetMapping("/category")
	public String showCategoryPage(Model model) {
		//return listCategoryPage(model, 10, 1);
		
		model.addAttribute("pagePath", "category");
		model.addAttribute("pageTitle", "Danh mục thiết bị - SDH");
		
		return "admin/category";
		
	}
	
	@GetMapping("/category/{pageSize}/page/{pageNumber}")
	public String listCategoryPage(Model model,
			@PathVariable("pageSize") int pageSize,
			@PathVariable("pageNumber") int currentPage) {
		
		Page<Category> page = categoryServices.findAll(currentPage - 1, pageSize);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		
		List<Category> listCategorys = page.getContent(); 
		model.addAttribute("pagePath", "category");
		model.addAttribute("pageTitle", "Danh mục thiết bị - SDH");
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listCategorys", listCategorys);
		return "admin/category";
	}
	
	
	@PostMapping("/add_category")
	public String addCategory(Model model, Category category) {
		cateRepo.save(category);
		
		return "redirect:/admin/category";
	}
}
