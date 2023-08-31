package vux.codejava.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vux.codejava.entity.District;
import vux.codejava.exception.DistrictNotFoundException;
import vux.codejava.service.DistrictServices;

@RestController
//@CrossOrigin
@RequestMapping(value="/api")
public class DistrictRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DistrictRestController.class);
	
	@Autowired
	private DistrictServices districtServices;	
	
	@GetMapping("/district/list/{id}")	
	public ResponseEntity<List<District>> listAll(@PathVariable("id") String strId){
		System.out.println("listAll district REST API ...");
		LOGGER.trace("listAll district REST API ...");
		if(!strId.equalsIgnoreCase("all")) {
			try {
				Long id = Long.valueOf(strId);
				System.out.println("Load district " + id);
				LOGGER.info("Load district list with id ", strId);
				return ResponseEntity.ok(districtServices.listDistrictById(id));
			}catch (Exception e) {
				LOGGER.error("error district convert id ", strId);
				return ResponseEntity.ok(districtServices.findAll());
			}
			
		}else {
			System.out.println("Load all district");
			LOGGER.info("district list all ");
			return ResponseEntity.ok(districtServices.findAll());
		}
		
		//return disRepo.listDistrist();
		
	}
	
	/*
	@RequestMapping(value="/district/list/{id}", method=RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<District> listAll(@PathVariable("id") String strId){
		System.out.println("listAll district REST API ...");
		LOGGER.trace("listAll district REST API ...");
		if(!strId.equalsIgnoreCase("all")) {
			
			try {
				Long id = Long.valueOf(strId);
				System.out.println("Load district " + id);
				LOGGER.info("Load district list with id ", strId);
				return districtServices.listDistrictById(id);
			}catch (Exception e) {
				LOGGER.error("error district convert id ", strId);
				return districtServices.findAll();
			}
		}else {
			System.out.println("Load all district");
			LOGGER.info("district list all ");
			return districtServices.findAll();
		}
		
		//return disRepo.listDistrist();
		
	}
	*/
	@PostMapping("/district/save")
	public ResponseEntity<District> save(@RequestBody @Valid District district) {
		District saveDistrict = districtServices.save(district);
		return new ResponseEntity<District>(saveDistrict, HttpStatus.CREATED);
	}
	
	@GetMapping("/district/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) throws DistrictNotFoundException {
		return ResponseEntity.ok(districtServices.delete(id));
	}
}
