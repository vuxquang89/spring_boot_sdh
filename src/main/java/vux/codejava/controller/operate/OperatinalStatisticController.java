package vux.codejava.controller.operate;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import vux.codejava.lib.Convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vux.codejava.entity.CustomUserDetails;
import vux.codejava.entity.operate.CableLink;
import vux.codejava.entity.operate.CableType;
import vux.codejava.entity.operate.Event;
import vux.codejava.entity.operate.OperationalStatistics;
import vux.codejava.entity.operate.ServiceBackbone;
import vux.codejava.entity.operate.ServiceCustomer;
import vux.codejava.entity.operate.ServiceInternal;
import vux.codejava.entity.operate.Status;
import vux.codejava.service.operate.CableLinkServices;
import vux.codejava.service.operate.CableTypeServices;
import vux.codejava.service.operate.EventServices;
import vux.codejava.service.operate.OperationalStatisticServices;
import vux.codejava.service.operate.ServiceBackboneServices;
import vux.codejava.service.operate.ServiceCustomerServices;
import vux.codejava.service.operate.ServiceInternalServices;
import vux.codejava.service.operate.StatusServices;

@Controller
public class OperatinalStatisticController {

	@Autowired
	private CableLinkServices cableLinkServices;
	
	@Autowired
	private CableTypeServices cableTypeServices;
	
	@Autowired
	private ServiceInternalServices internalServices;
	
	@Autowired
	private ServiceCustomerServices customerServices;
	@Autowired
	private ServiceBackboneServices backboneServices;
	@Autowired
	private EventServices eventServices;
	@Autowired
	private StatusServices statusServices;
	
	@Autowired
	private OperationalStatisticServices services;
	
	@GetMapping("/operate")
	public String viewOprate(Model model) {	
		
		model.addAttribute("pageTitle", "Lịch thống kê");
		model.addAttribute("pagePath", "operate");
		return "operate";
	}
	
	@GetMapping("/operate/add")
	public String viewFormOperate(Model model,
			Principal principal) {
		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		List<CableType> listCableTypes = cableTypeServices.findAll();
		List<CableLink> listCableLinks = cableLinkServices.findByDistrict(listCableTypes.get(0).getId(), userDetails.getDistrict());
		
		List<Event> listEvents = eventServices.findAll();
		List<ServiceInternal> listServiceInternals = internalServices.findAll();
		List<ServiceCustomer> listServiceCustomers = customerServices.findAll();
		List<ServiceBackbone> listServiceBackbones = backboneServices.findAll();
		List<Status> listStatus = statusServices.findAll();
		
		model.addAttribute("pageTitle", "Thêm thống kê");
		model.addAttribute("pagePath", "operate");
		model.addAttribute("listCableTypes", listCableTypes);
		model.addAttribute("listCableLinks", listCableLinks);
		model.addAttribute("listEvents", listEvents);
		model.addAttribute("listServiceInternals", listServiceInternals);
		model.addAttribute("listServiceCustomers", listServiceCustomers);
		model.addAttribute("listServiceBackbones", listServiceBackbones);
		model.addAttribute("listStatus", listStatus);
		
		return "add_operate";
	}
	
	@GetMapping("/operate/edit/{id}")
	public String viewFormEditOperate(Model model, 
			@PathVariable("id") Long id) {
		//CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		OperationalStatistics operate = services.findById(id);		
		
		List<CableType> listCableTypes = cableTypeServices.findAll();
		Long cableTypeId = cableLinkServices.getCableLinkById(operate.getCableLink().getId()).getCableType().getId();
		List<CableLink> listCableLinks = cableLinkServices.findById(cableTypeId);
		System.out.println("OPERATE ......................... ID : " + operate.getId());
		System.out.println("find cable type Id : " + cableTypeId);
		List<Event> listEvents = eventServices.findAll();
		List<ServiceInternal> listServiceInternals = internalServices.findAll();
		List<ServiceCustomer> listServiceCustomers = customerServices.findAll();
		List<ServiceBackbone> listServiceBackbones = backboneServices.findAll();
		List<Status> listStatus = statusServices.findAll();
		
		model.addAttribute("pageTitle", "Sửa thống kê");
		model.addAttribute("pagePath", "operate");
		model.addAttribute("listCableTypes", listCableTypes);
		model.addAttribute("cableTypeId", cableTypeId);
		model.addAttribute("listCableLinks", listCableLinks);
		model.addAttribute("listEvents", listEvents);
		model.addAttribute("listServiceInternals", listServiceInternals);
		model.addAttribute("listServiceCustomers", listServiceCustomers);
		model.addAttribute("listServiceBackbones", listServiceBackbones);
		model.addAttribute("listStatus", listStatus);
		
		
		model.addAttribute("operate", operate);

		
		return "edit_operate";
	}
	
	@PostMapping("/operate/do_delete")
	public String deleteOperate(Model model,
			@PathParam("operateId") Long operateId, 
			@PathParam("operateDate") String operateDate,
			Principal principal) {
		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		services.delete(userDetails.getUsername(), LocalDateTime.now(),  operateId);
		System.out.println("DO DELETE Operate Id : " + operateId);
		return "redirect:/operate/list/"+operateDate;
	}
	
	@PostMapping("/operate/update/{id}")
	public String updateOperate(Model model, @PathVariable("id") Long id, 
			@ModelAttribute OperationalStatistics operational, Principal principal) {
		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		List<String> msgError = new ArrayList<String>();
		boolean check = true;
		System.out.println("update operate...");
		System.out.println(operational.getId());
		if(operational.getLocalStartTime() == null || operational.getLocalEndTime() == null) {
			msgError.add("Chưa chọn thời gian thực hiện");
			check = false;
		}
		if(operational.getNote().trim().length() < 20) {
			msgError.add("Cần nhập ghi chú lớn hơn 20 ký tự");
			check = false;
		}
		if(check) {
			operational.setProcessingTime(Convert.durationBetweenLocalDateTime(operational.getLocalStartTime(), 
					operational.getLocalEndTime()));
			if(operational.getProcessingTime() > 0) {
				//operational.setId(id);
				operational.setUpdateTime(LocalDateTime.now());
				
				operational.setUserUpdate(userDetails.getUsername());
				operational.setDistrict(userDetails.getDistrict());
				//OperationalStatistics operateSave = services.save(operational);
				services.update(operational.getUserUpdate(), operational.getCableLink().getId(), operational.getEvent().getId(), 
						operational.getLocalStartTime(), operational.getLocalEndTime(), operational.getProcessingTime(), 
						operational.getServiceInternal().getId(), operational.getServiceCustomer().getId(), 
						operational.getServiceBackbone().getId(), operational.getStatus().getId(), operational.getNote(), 
						LocalDateTime.now(), id);
				LocalDate localDate = operational.getLocalStartTime().toLocalDate();
				return "redirect:/operate/list/"+localDate.toString();
			}else {
				msgError.add("Thời gian kết thúc phải lớn hơn thời gian bắt đầu");
				
			}
		}
		model.addAttribute("msg", msgError);
		return viewFormEditOperate(model, operational.getId());
	}
	
	@PostMapping("/operate/do_add")
	public String addOperate(Model model, OperationalStatistics operationalStatistics, Principal principal) {
		CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		List<String> msgError = new ArrayList<String>();
		boolean check = true;
		if(operationalStatistics.getLocalStartTime() == null || operationalStatistics.getLocalEndTime() == null) {
			msgError.add("Chưa chọn thời gian thực hiện");
			check = false;
		}
		if(operationalStatistics.getNote().trim().length() < 20) {
			msgError.add("Cần nhập ghi chú lớn hơn 20 ký tự");
			check = false;
		}
		
		if(check){			
			operationalStatistics.setProcessingTime(Convert.durationBetweenLocalDateTime(operationalStatistics.getLocalStartTime(), 
					operationalStatistics.getLocalEndTime()));
			if(operationalStatistics.getProcessingTime() > 0) {
				operationalStatistics.setCreateTime(LocalDateTime.now());
				operationalStatistics.setUpdateTime(LocalDateTime.now());
				
				operationalStatistics.setUserCreate(userDetails.getUsername());
				operationalStatistics.setUserUpdate(userDetails.getUsername());
				operationalStatistics.setDistrict(userDetails.getDistrict());
				OperationalStatistics operateSave = services.save(operationalStatistics);
				
				System.out.println("save operate...");
				System.out.println(operateSave.getId());
				LocalDate localDate = operationalStatistics.getLocalStartTime().toLocalDate();
				return "redirect:/operate/list/"+localDate.toString();
			}else {
				msgError.add("Thời gian kết thúc phải lớn hơn thời gian bắt đầu");
				
			}
		}
		model.addAttribute("msg", msgError);
		return viewFormOperate(model, principal);
	}
	
	@GetMapping("/operate/list/{date}")
	public String getOperateWithDate(Model model, @PathVariable("date") String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<String> msgError = new ArrayList<String>();
		model.addAttribute("pageTitle", "Danh sách thống kê theo ngày");
		model.addAttribute("pagePath", "operate");
		try { 
			LocalDate localDate = LocalDate.parse(date, formatter);
			
			List<OperationalStatistics> listOperational = services.getOperationalByDate(localDate);
			
			model.addAttribute("date", localDate);
			model.addAttribute("listOperational", listOperational);
		}catch (Exception e) {
			msgError.add("Nên chọn thời gian đúng định dạng yyyy-MM-dd");
			model.addAttribute("date", null);
			model.addAttribute("msg", msgError);
		}
		return "list_operate";
	}
}
