package vux.codejava.controller.shift;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vux.codejava.entity.CustomUserDetails;
import vux.codejava.entity.shift.DetailValue;
import vux.codejava.entity.shift.KeyEntity;
import vux.codejava.entity.shift.ShiftDetailEntity;
import vux.codejava.entity.shift.ShiftEntity;
import vux.codejava.entity.shift.ShiftRequest;
import vux.codejava.helper.ShiftExcelHelper;
import vux.codejava.response.ShiftResponse;
import vux.codejava.service.GoogleApiService;
import vux.codejava.service.KeyServices;
import vux.codejava.service.ShiftDetailServices;
import vux.codejava.service.ShiftServices;
import vux.codejava.util.PrincipalObject;

@Controller
public class ShiftController {
	
	@Autowired
	private KeyServices keyServices;
	
	@Autowired
	private PrincipalObject principalObject;
	
	@Autowired
	private ShiftServices shiftServices;
	
	@Autowired
	private ShiftDetailServices shiftDetailServices;
	
	@Autowired
	private GoogleApiService googleApiService;
	
	@GetMapping("/shift")
	public String viewOprate(Model model
			,Principal principal
			) {	
		
		
//		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		CustomUserDetails userDetails = principalObject.getCustomUserDetails(principal);
		String district = userDetails.getDistrict();
		String fullname = userDetails.getName();
		boolean inShift = false;
		
		model.addAttribute("pageTitle", "Giao - Nhận ca");
		model.addAttribute("pagePath", "shift");
		
				
		List<KeyEntity> keyEntities = keyServices.findAll();
		KeyEntity keyMT = getEntity(keyEntities, "MT");
		KeyEntity keyMB = getEntity(keyEntities, "MB");
		
		ShiftResponse shiftResponseMT = new ShiftResponse();
		shiftResponseMT.setStatus(keyMT.getKeyStatus());
		shiftResponseMT.setDistrict("MT");
		shiftResponseMT.setUsername(keyMT.getUsername());
		shiftResponseMT.setShiftEntity(shiftServices.findByKeyCode(keyMT.getKeyCode()));
		shiftResponseMT.setAction();
		shiftResponseMT.setInShift(inShift);
		
		ShiftResponse shiftResponseMB = new ShiftResponse();
		shiftResponseMB.setStatus(keyMB.getKeyStatus());
		shiftResponseMB.setDistrict("MB");
		shiftResponseMB.setUsername(keyMB.getUsername());
		shiftResponseMB.setShiftEntity(shiftServices.findByKeyCode(keyMB.getKeyCode()));
		shiftResponseMB.setAction();
		shiftResponseMB.setInShift(inShift);
		
//		try {
//			List<String> nameShifts = googleApiService.readDataFromGoogleSheet(district);
			
//			for(String name : nameShifts) {
//				if(name.toUpperCase().indexOf(fullname.toUpperCase()) >= 0) {
//					
//					inShift = true;
//					break;
//				}
//			}
//			return false;
//		} catch (GeneralSecurityException | IOException e) {
//			// TODO Auto-generated catch block
//			
//			System.out.println("error");
//			
//			e.printStackTrace();
//			model.addAttribute("error",e);
////			return false;
//		}
		
		if(district.equalsIgnoreCase("MT")) {
			if(keyMT.getKeyStatus() && keyMT.getUsername().equalsIgnoreCase(userDetails.getUsername())) {
				shiftResponseMT.setUsername("me");
				shiftResponseMT.setForm(false);
			}else {
				shiftResponseMT.setInShift(isInShift(fullname, district));
				shiftResponseMT.setForm(true);
			}
			shiftResponseMB.setForm(true);
			
		}else if (district.equalsIgnoreCase("MB")) {
			if(keyMB.getKeyStatus() && keyMB.getUsername().equalsIgnoreCase(userDetails.getUsername())) {
				shiftResponseMB.setUsername("me");
				shiftResponseMB.setForm(false);
			}else {
				shiftResponseMB.setInShift(isInShift(fullname, district));
				shiftResponseMB.setForm(true);
			}
			model.addAttribute("formMT", true);
			shiftResponseMT.setForm(true);
		}else {
			KeyEntity sg = getEntityByUsername(keyEntities, userDetails.getUsername());
			if(sg != null) {
				
				if(sg.getKeyStatus()) {
					model.addAttribute("statusSG", true);
					if(sg.getDistrict().equalsIgnoreCase("MT")) {
						shiftResponseMT.setUsername("me");
						shiftResponseMT.setForm(false);
						shiftResponseMB.setForm(true);
					}else {
						shiftResponseMB.setUsername("me");
						shiftResponseMT.setForm(true);
						shiftResponseMB.setForm(false);
					}
				}else {
					model.addAttribute("statusSG", false);
					shiftResponseMT.setInShift(isInShift(fullname, "MT"));
					shiftResponseMT.setForm(true);
					shiftResponseMB.setForm(true);
					if(shiftResponseMT.getInShift()) {
						shiftResponseMB.setInShift(false);
					}else {
						shiftResponseMB.setInShift(isInShift(fullname, "MB"));
					}
					
				}
			}else {
				model.addAttribute("statusSG", false);
				shiftResponseMT.setInShift(isInShift(fullname, "MT"));
				shiftResponseMT.setForm(true);
				shiftResponseMB.setForm(true);
				if(shiftResponseMT.getInShift()) {
					shiftResponseMB.setInShift(false);
				}else {
					shiftResponseMB.setInShift(isInShift(fullname, "MB"));
				}
			}
			
			
		}
		
		model.addAttribute("shiftMT", shiftResponseMT);
		model.addAttribute("shiftMB", shiftResponseMB);
		model.addAttribute("district", district);
		
		
		return "shift";
	}
	
	private boolean isInShift(String fullname, String district) {
		try {
			List<String> nameShifts = googleApiService.readDataFromGoogleSheet(district);
			
			for(String name : nameShifts) {
				if(name.toUpperCase().indexOf(fullname.toUpperCase()) >= 0) {
					
					return true;
				}
			}
			return false;
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("error");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * nhan ca
	 * @param model
	 * @param request
	 * @param principal
	 * @return
	 */
	@PostMapping("/shift/action")
	public String shiftAction(
			Model model,
			@ModelAttribute ShiftRequest request,
			Principal principal
			) {
//		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		CustomUserDetails userDetails = principalObject.getCustomUserDetails(principal);
		String shiftDistrict = request.getDistrict();
		KeyEntity keyEntity = keyServices.findByDistrict(shiftDistrict);
		if(keyEntity != null) {
			if(keyEntity.getKeyStatus()) {
				System.out.println(keyEntity.getDistrict() + " dang trong ca truc");
				
			}else {
				String keyCodeNow = keyEntity.getKeyCode();
				ShiftEntity shiftEntity = shiftServices.findByKeyCode(keyCodeNow);
				//update khi nguoi khac nhan ca
				shiftEntity.setNoteReceive(request.getNoteReceive());
				shiftEntity.setUserChangeNoteReceive(userDetails.getUsername());
				shiftServices.save(shiftEntity);
				
				ShiftEntity shiftEntityNew = convertNewShiftEntity(shiftEntity);
				
				String keyCode = RandomStringUtils.randomAlphanumeric(20);
				keyEntity.setKeyCode(keyCode);
				keyEntity.setUsername(userDetails.getUsername());
				keyEntity.setKeyStatus(true);
				keyServices.save(keyEntity);
				
				shiftEntityNew.setKeyCode(keyCode);
				shiftEntityNew.setUserReceive(userDetails.getUsername());
				shiftEntityNew.setUserShift(userDetails.getUsername());
				shiftEntityNew.setDateReceive(LocalDateTime.now());
				shiftEntityNew.setDateShift(LocalDateTime.now());
				shiftEntityNew.setDistrict(shiftDistrict);
				//shiftEntityNew.setShiftDetails(convertShiftDetail(shiftEntityNew, shiftEntity.getShiftDetails()));
				shiftEntityNew = shiftServices.save(shiftEntityNew);
				
				shiftDetailServices.saveAll(convertShiftDetail(shiftEntityNew, shiftEntity.getShiftDetails()));
			}
			
			//return viewOprate(model, principal);
			return "redirect:/shift";
		}else {
			return "403";
		}
		
		
	}
	
	/**
	 * giao ca
	 * @param model
	 * @param request
	 * @param principal
	 * @return
	 */
	@PostMapping("/shift")
	public String addShift(Model model
			,@ModelAttribute ShiftRequest request
			,Principal principal
			) {
//		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		CustomUserDetails userDetails = principalObject.getCustomUserDetails(principal);
		KeyEntity keyEntity = keyServices.findByUsernameAndDistrict(userDetails.getUsername(), request.getDistrict()).get();
		
		if(keyEntity != null) {
			ShiftEntity shiftEntity = shiftServices.findByKeyCode(keyEntity.getKeyCode());
			shiftEntity.setAction(request.getAction());
			shiftEntity.setNote(request.getNote());
			//shiftEntity.setNoteAction(request.getNoteAction());
			shiftEntity.setUserShift(userDetails.getUsername());
			shiftEntity.setDateShift(LocalDateTime.now());
			shiftEntity.setShiftDetails(convertActiontoList(shiftEntity.getShiftDetails(), request));
			//shiftEntity.setDistrict(request.getDistrict());
			keyEntity.setKeyStatus(false);
			keyEntity.setUsername("null");
			keyServices.save(keyEntity);
			shiftServices.save(shiftEntity);
		}else {
			System.out.println("Khong trong ca truc");
		}
		
		
		return "redirect:/shift";
	}
	
	/**
	 * export file excel
	 * @param response
	 * @param month
	 * @param filter
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/shift/export/excel/{month}/{filter}")
    public ResponseEntity<?> exportToExcel(HttpServletResponse response,
    		@PathVariable("month") String month,
    		@PathVariable("filter") int filter,
    		Principal principal) throws IOException {
		CustomUserDetails userDetails = principalObject.getCustomUserDetails(principal);
		//List<ShiftEntity> listShifts = shiftServices.listAll();
		List<ShiftEntity> listShifts = shiftServices.listByDateReceive(month);//sheet giao-nhan ca
		List<ShiftEntity> listShiftActions = shiftServices.listByDateShift(month);//sheet su kien
		
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        
        
        ShiftExcelHelper excelExport = new ShiftExcelHelper(listShifts,listShiftActions);
        //excelExport.export(response, filter, month);
        
        InputStreamResource file = new InputStreamResource(excelExport.export(filter, month));

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user_" +userDetails.getUsername() +"_"+ currentDateTime + ".xlsx")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(file);
        
        
        /*
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        ShiftExcelExportUtil excelExportUtil = new ShiftExcelExportUtil(listShifts);
        excelExportUtil.export(response, filter, month);
        */
	}
	
	private KeyEntity getEntity(List<KeyEntity> keyEntities, String district) {
		for(KeyEntity key : keyEntities) {
			if(key.getDistrict().equalsIgnoreCase(district)) {
				return key;
			}
		}
		return null;
	}
	
	private KeyEntity getEntityByUsername(List<KeyEntity> keyEntities, String username) {
		for(KeyEntity key : keyEntities) {
			if(key.getUsername().equalsIgnoreCase(username)) {
				return key;
			}
		}
		return null;
	}
	
	private ShiftEntity convertNewShiftEntity(ShiftEntity shiftEntity) {
		ShiftEntity shiftNew = new ShiftEntity();
		if(shiftEntity != null) {
			shiftNew.setNote(shiftEntity.getNote());
			//shiftNew.setNoteAction(shiftEntity.getNoteAction());
			shiftNew.setAction(shiftEntity.getAction());
			//shiftNew.setShiftDetails(shiftEntity.getShiftDetails());
		}
		
		return shiftNew;
	}
	
	private List<ShiftDetailEntity> convertShiftDetail(ShiftEntity shiftEntity, List<ShiftDetailEntity> shiftDetailsOld){
		List<ShiftDetailEntity> shiftDetails = new ArrayList<ShiftDetailEntity>();
		for(ShiftDetailEntity shiftDetail : shiftDetailsOld) {
			ShiftDetailEntity shift = new ShiftDetailEntity();
			shift.setAction(shiftDetail.getAction());
			shift.setCables(shiftDetail.getCables());
			shift.setNoteAction(shiftDetail.getNoteAction());
			shift.setShift(shiftEntity);
			shiftDetails.add(shift);
		}
		return shiftDetails;
	}
	
	private List<ShiftDetailEntity> convertActiontoList(List<ShiftDetailEntity> shiftDetails, ShiftRequest request){
		//List<ShiftDetailEntity> shiftDetails = new ArrayList<ShiftDetailEntity>();
		for(ShiftDetailEntity shiftDetail : shiftDetails) {
			DetailValue detailValue = request.getValues(shiftDetail.getCables().getCableCode());
			shiftDetail.setAction(detailValue.getAction());
			if(detailValue.getAction() == 0) {
				shiftDetail.setNoteAction("");
			}else {
				shiftDetail.setNoteAction(detailValue.getNoteAction());
			}
			
		}
		return shiftDetails;
	}
}
