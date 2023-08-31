package vux.codejava.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vux.codejava.entity.Site;
import vux.codejava.exception.SiteNotFoundException;
import vux.codejava.response.SiteResponse;
import vux.codejava.service.SiteServices;

@RestController
@RequestMapping("/api")
public class SiteRestController {

	@Autowired
	private SiteServices services;
	
	@PostMapping("/site/save")
	public ResponseEntity<SiteResponse> save(@RequestBody @Valid Site site) {
		System.out.println("Save Site REST API....");
		System.out.println(site.getName());
		System.out.println(site.getDistrict().getId());
		SiteResponse saveSite = services.save(site);
		
		return new ResponseEntity<SiteResponse>(saveSite, HttpStatus.CREATED);
	}
	
	@GetMapping("/site/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws SiteNotFoundException {
		return ResponseEntity.ok(services.delete(id));
	}
	
	@GetMapping("/district/site/list/{id}")
	public ResponseEntity<List<Site>> listSiteByDistrict(@PathVariable("id") Long id){
		return ResponseEntity.ok(services.findAllByDistrict(id));
	}
}
