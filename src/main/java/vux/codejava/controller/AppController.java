package vux.codejava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vux.codejava.entity.Category;
import vux.codejava.entity.District;
import vux.codejava.entity.Site;
import vux.codejava.response.ExcellentIndividualResponse;
import vux.codejava.service.CategoryServices;
import vux.codejava.service.DistrictServices;
import vux.codejava.service.ExcellentIndividualService;
import vux.codejava.service.SiteServices;

@Controller
public class AppController {
	@Autowired
	private DistrictServices districtServices;
	@Autowired
	private CategoryServices categoryServices;
	@Autowired
	private SiteServices siteServices;
	
	@Autowired
	private ExcellentIndividualService excellentIndividualService;
	

	@GetMapping({"/", "/index", "/home"})
	public String viewHomePage(Model model) {
		List<ExcellentIndividualResponse> excellentIndividuals = excellentIndividualService.findAll();
			
		model.addAttribute("pageTitle", "Trang chủ");
		model.addAttribute("pagePath", "home");
		model.addAttribute("excellentIndividuals", excellentIndividuals);
		return "index";
	}
	
	@GetMapping({"/sites"})
	public String viewSitesPage(Model model) {
		List<District> districts = districtServices.findAll();
		List<Site> sites = null;
		if(districts != null && districts.size() > 0) {
			System.out.println("load district");
			System.out.println(districts.size());
			sites = siteServices.findAllByDistrict(districts.get(0));
		}
		List<Category> listCategorys = categoryServices.listAll();
		
		model.addAttribute("pageTitle", "Nhà trạm");
		model.addAttribute("pagePath", "sites");
		model.addAttribute("listCategorys", listCategorys);
		
		model.addAttribute("districts", districts);
		model.addAttribute("sites", sites);
		return "sites";
	}
	
	@GetMapping("/list_users")
	public String viewListUser() {
		return "list_users";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "login";
	}
}
