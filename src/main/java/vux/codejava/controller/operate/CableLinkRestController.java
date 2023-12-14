package vux.codejava.controller.operate;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vux.codejava.entity.CustomUserDetails;
import vux.codejava.entity.operate.CableLink;
import vux.codejava.service.operate.CableLinkServices;
import vux.codejava.util.PrincipalObject;

@RestController
@RequestMapping(value="/api")
public class CableLinkRestController {

	@Autowired
	private CableLinkServices cableLinkServices;
	@Autowired
	private PrincipalObject principalObject;
	
	@GetMapping("/operate/cablelink/list/add/{id}")
	public ResponseEntity<List<CableLink>> getAddCableLink(
			@PathVariable("id") Long cableTypeId,
			Principal principal){
//		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		CustomUserDetails userDetails = principalObject.getCustomUserDetails(principal);
		System.out.println("REST API CableLink....");
		System.out.println(cableTypeId);
		List<CableLink> listCableLink;
		if(userDetails.getDistrict().equalsIgnoreCase("HCM")) {
			listCableLink = cableLinkServices.findById(cableTypeId);
		}else {
			listCableLink = cableLinkServices.findByDistrict(cableTypeId, userDetails.getDistrict());
		}
		
		System.out.println(listCableLink.size());
		return ResponseEntity.ok(listCableLink);
	}
	
	@GetMapping("/operate/cablelink/list/edit/{id}")
	public ResponseEntity<List<CableLink>> getEditCableLink(
			@PathVariable("id") Long cableTypeId){
		//CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		System.out.println("REST API CableLink....");
		System.out.println(cableTypeId);
		List<CableLink> listCableLink = cableLinkServices.findById(cableTypeId);
		System.out.println(listCableLink.size());
		return ResponseEntity.ok(listCableLink);
	}
	
}
