package vux.codejava.controller.operate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vux.codejava.entity.CustomMessage;
import vux.codejava.entity.operate.CableType;
import vux.codejava.entity.operate.EventCalendar;
import vux.codejava.entity.operate.OperationalStatistics;
import vux.codejava.service.operate.CableTypeServices;
import vux.codejava.service.operate.OperationalStatisticServices;
import vux.codejava.util.Color;

@RestController
@RequestMapping("/api")
public class OperatinalStatisticRestController {

	@Autowired
	private OperationalStatisticServices services;
	
	@Autowired
	private CableTypeServices cableTypeServices;
	
	@GetMapping("/operate/checkinput/{date}/cabletype/{id}")
	public ResponseEntity<CustomMessage> checkInputOperatinal(
			@PathVariable("date") String date,
			@PathVariable("id") Long cableTypeId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		CustomMessage customMessage = new CustomMessage();
		try {
			LocalDate localDate = LocalDate.parse(date, formatter);
			List<OperationalStatistics> listOperatinal = services.getOperatinalInputDate(localDate, cableTypeId);
			if(listOperatinal.size() > 0) {
				CableType cableType = cableTypeServices.findCableTypeById(cableTypeId);
				customMessage.setStatus("400");
				customMessage.setMessage("Ngày "+localDate+", "+cableType.getName()+" đang có "+listOperatinal.size()+" lịch CR.");
			}else {
				customMessage.setStatus("300");
			}
		}catch(Exception e) {
			customMessage.setStatus("305");
			customMessage.setMessage("Lỗi định dạng ngày tháng (yyyy-MM-dd)");
		}
		return ResponseEntity.ok(customMessage);
	}
	
	@GetMapping("/operate/calendar/list/{month}/{year}")
	public ResponseEntity<List<EventCalendar>> getOperateCalendar(
			@PathVariable("month") Integer month,
			@PathVariable("year") Integer year){
		System.out.println("month ===>" + month);
		//CustomUserDetails userDetails = (CustomUserDetails)((Authentication)principal).getPrincipal();
		List<OperationalStatistics> listOperationalStatistics = services.getOperationalByMonth(month, year);
		List<EventCalendar> listEventCalendar = new ArrayList<EventCalendar>();
		for(OperationalStatistics operate : listOperationalStatistics) {
			EventCalendar eventCalendar = new EventCalendar();
			eventCalendar.setId(operate.getId());
			eventCalendar.setStart(operate.getStartTime());
			eventCalendar.setEnd(operate.getEndTime());
			String title = operate.getEvent().getNameAcronym() + " "
					+ operate.getCableLink().getCableType().getName() 
					+ " tuyến " + operate.getCableLink().getName();
			eventCalendar.setTitle_acronym(title);
			eventCalendar.setColor(getColor(operate.getStatus().getId(), operate.getProcessingTime()));
			listEventCalendar.add(eventCalendar);
		}
		return ResponseEntity.ok(listEventCalendar);
	}
	
	@GetMapping("/operate/calendar/event/{eventId}")
	public ResponseEntity<EventCalendar> getOperateCalendarEvent(
			@PathVariable("eventId") Long eventId){
		OperationalStatistics operate = services.findById(eventId);
		EventCalendar eventCalendar = new EventCalendar();
		eventCalendar.setId(operate.getId());
		eventCalendar.setStart(operate.getStartTime());
		eventCalendar.setEnd(operate.getEndTime());
		eventCalendar.setStatus(operate.getStatus().getName());
		String title = operate.getEvent().getName() + " "
				+ operate.getCableLink().getCableType().getName() 
				+ " tuyến " + operate.getCableLink().getName();
		eventCalendar.setTitle(title);
		eventCalendar.setNote(operate.getNote());
		eventCalendar.setColor(getColor(operate.getStatus().getId(), operate.getProcessingTime()));
		eventCalendar.setProcessingTime(operate.getProcessingTime());
		return ResponseEntity.ok(eventCalendar);
	}
	
	private String getColor(long i, int processingTime) {
		String color = "";
		if(processingTime >= 360) {
			color = Color.RED;
		}else if(i == 1) {
			// doi lich
			color = Color.YELLOW;
		}else if(i == 2) {
			// ke hoach du kien
			color = Color.ORANGE;
		}else {
			// hoan thanh/ lich moi
			color = Color.BLUE;
		}
		System.out.println(processingTime + " : " + i + " = " + color);
		return color;
	}
}
