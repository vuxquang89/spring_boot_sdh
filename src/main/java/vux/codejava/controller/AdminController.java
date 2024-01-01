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
@RequestMapping("/admin")
public class AdminController {
	
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
		
		return "admin/index";
	}
	
	@GetMapping("/device")
	public String showDevicePage(Model model) {
		List<Category> listCategorys = categoryServices.listAll();
		
		model.addAttribute("pagePath", "device");
		model.addAttribute("pageTitle", "Thiết bị - SDH");
		model.addAttribute("listCategorys", listCategorys);
		
		return "admin/device";
	}
	
	@GetMapping("/port")
	public String showPortPage(Model model) {
		
		model.addAttribute("pagePath", "port");
		model.addAttribute("pageTitle", "Thiết bị - SDH");
		
		return "admin/port";
	}
	
	@GetMapping("/site")
	public String showSitePage(Model model) {
		List<District> districts = districtServices.findAll();
		
		List<Site> sites = null;
		
		if(districts != null && districts.size() > 0) {
			sites = siteServices.findAllByDistrict(districts.get(0));
		}
		
		List<Category> listCategorys = categoryServices.listAll();
		/*
		ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = null;
		try {
			json = mapper.writeValueAsString(listCategorys);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		model.addAttribute("pagePath", "site");
		model.addAttribute("pageTitle", "Quản lý Sites - SDH");
		model.addAttribute("listCategorys", listCategorys);
		
		model.addAttribute("districts", districts);
		model.addAttribute("sites", sites);
		
		return "admin/site";
	}
	
	//--------------chat real time---------------------
	//https://hocspringboot.net/2020/12/11/ung-dung-chat-realtime-websocket/
	
	//--------------select option----------------------
	//https://magento.stackexchange.com/questions/277693/how-to-keep-lable-choosed-on-select-option-after-refresh-page
	//https://stackoverflow.com/questions/41174516/keep-option-after-a-refresh-in-html-using-javascript
	
	//https://stackoverflow.com/questions/24203220/thselected-a-number-in-a-select-option-with-thymeleaf-doesnt-work
	//https://www.wimdeblauwe.com/blog/2021/04/16/using-html-select-options-with-thymeleaf/
	
	//--------------validation------------------------
	//https://www.springboottutorial.com/spring-boot-validation-for-rest-services
	//https://www.appsdeveloperblog.com/handle-exceptions-spring-boot/
	//https://www.javaguides.net/2021/03/validation-in-spring-boot-rest-api-with-hibernate-validator.html
	//https://mkyong.com/spring-boot/spring-rest-validation-example/
	//https://mkyong.com/spring-boot/spring-rest-error-handling-example/
	//https://stackoverflow.com/questions/70201352/spring-boot-rest-api-handling-unique-constraint
	
	//https://howtodoinjava.com/spring-boot2/spring-rest-request-validation/
	//https://www.codejava.net/frameworks/spring-boot/rest-api-request-validation-examples
	//https://www.baeldung.com/spring-boot-bean-validation
	//https://www.baeldung.com/exception-handling-for-rest-with-spring
	//https://viblo.asia/p/dung-validation-bang-tay-trong-spring-boot-phan-1-3P0lPGyoZox
	
	//--------------phan trang---------------------
	//https://www.codelean.vn/2020/01/loc-va-phan-trang-voi-ung-dung-quan-ly.html
	
	//https://openplanning.net/11797/phan-trang-pagination-trong-java-hibernate
	//https://openplanning.net/11223/tao-ra-he-thong-bang-tu-cac-class-entity-trong-hibernate
	//https://techmaster.vn/posts/37076/springboot-huong-dan-su-dung-hibernate-onetomany-annotation
	//https://examples.javacodegeeks.com/enterprise-java/hibernate/hibernate-foreign-key-example/
	//https://www.digitalocean.com/community/tutorials/spring-mvc-hibernate-mysql-integration-crud-example-tutorial
	//https://www.bezkoder.com/jpa-repository-query/
	
	//---------JPA---------
	//https://viblo.asia/p/spring-boot-12-spring-jpa-method-atquery-Qbq5Q4nGlD8
	//https://viblo.asia/p/tranh-select-khi-insert-entity-trong-jpa-voi-proxy-entity-RnB5pArbKPG
	//https://thorben-janssen.com/native-queries-with-spring-data-jpa/
	//https://www.bezkoder.com/jpa-native-query/
	//https://vladmihalcea.com/how-to-map-a-manytoone-association-using-a-non-primary-key-column/
	//https://www.baeldung.com/jpa-join-types
	
	//https://mkyong.com/spring-boot/spring-boot-ajax-example/
	//https://stackoverflow.com/questions/66427154/jquery-ajax-call-spring-boot-controler-date-modification
	//https://www.bezkoder.com/jpa-one-to-many/
	//https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
	
	//https://www.youtube.com/watch?v=L8R93JC85kg
	
	//https://reflectoring.io/bean-validation-with-spring-boot/
	//https://o7planning.org/10683/create-a-shopping-cart-web-application-with-spring-boot-hibernate
	//https://techmaster.vn/posts/36877/xac-thuc-request-bang-spring-boot-validation
	//https://bushansirgur.in/spring-mvc-read-checkbox-values-from-java-model/
	//https://www.codejava.net/frameworks/spring-boot/spring-thymeleaf-form-multi-checkboxes-mapping-with-collection-example
	//https://stackoverflow.com/questions/37359851/how-to-receive-html-check-box-value-in-spring-mvc-controller
	
	/*------------ UPLOAD FILE ----------------*/
	//https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
	//https://www.codejava.net/frameworks/spring-boot/file-download-upload-rest-api-examples
	//https://www.bezkoder.com/spring-boot-file-upload/
	//https://www.javaguides.net/2018/11/spring-boot-2-file-upload-and-download-rest-api-tutorial.html
	//https://stackoverflow.com/questions/2320069/jquery-ajax-file-upload
	//https://dzone.com/articles/java-springboot-rest-api-to-uploaddownload-file-on
	//https://o7planning.org/11813/spring-boot-file-upload-with-jquery-ajax
	//https://mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
	//https://zetcode.com/spring/webjars/
	
	//https://frontbackend.com/spring-boot/spring-boot-upload-file-to-filesystem
	
	/*---------------ZOOM IMAGE--------------*/
	//https://github.com/worka/vanilla-js-wheel-zoom
	//https://codepen.io/stack-findover/pen/VwPgQQr
	//https://dev.to/stackfindover/zoom-image-point-with-mouse-wheel-11n3
	
	//https://frontbackend.com/thymeleaf/working-with-dates-in-thymeleaf
	//https://caodang.fpt.edu.vn/tin-tuc-poly/ha-noi-tin-sinh-vien/huong-dan-xay-dung-demo-crud-ajax-voi-spring-boot.html
}
