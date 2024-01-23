package vux.codejava.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vux.codejava.entity.Category;
import vux.codejava.entity.District;
import vux.codejava.entity.Site;
import vux.codejava.service.CategoryServices;
import vux.codejava.service.DistrictServices;
import vux.codejava.service.SiteServices;

@Controller
@RequestMapping("/editor")
public class EditorController {
	
	@Autowired
	private DistrictServices districtServices;
	
	@Autowired
	private CategoryServices categoryServices;
	
	@Autowired
	private SiteServices siteServices;

	@GetMapping(value = {"", "/dasboard"})
	public String dasboard(Model model, Principal principal) {
		List<District> listDistricts = districtServices.findAll();
		
		model.addAttribute("pagePath", "dasboard");
		model.addAttribute("pageTitle", "Khu vực - SDH");
		model.addAttribute("districtForm", new District());
		model.addAttribute("listDistricts", listDistricts);
		
		return "editor/index";
	}
	
	@GetMapping("/site")
	public String showSitePage(Model model) {
		List<District> districts = districtServices.findAll();
		
		List<Site> sites = null;
		
		if(districts != null && districts.size() > 0) {
			sites = siteServices.findAllByDistrict(districts.get(0));
		}
		
		List<Category> listCategorys = categoryServices.listAll();
		
		model.addAttribute("pagePath", "site");
		model.addAttribute("pageTitle", "Quản lý Sites - SDH");
		model.addAttribute("listCategorys", listCategorys);
		
		model.addAttribute("districts", districts);
		model.addAttribute("sites", sites);
		
		return "editor/site";
	}
}
