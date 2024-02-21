package vux.codejava.service.impl.operate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vux.codejava.entity.operate.OperationalStatistics;
import vux.codejava.repository.operate.OperationalStatisticsRepository;
import vux.codejava.service.operate.OperationalStatisticServices;

@Service
public class OperatinalStatisticImple implements OperationalStatisticServices{

	@Autowired
	private OperationalStatisticsRepository repo;
	
	@Override
	public List<OperationalStatistics> findAll() {
		return repo.findAll();
	}
	
	@Override
	public List<OperationalStatistics> getNotificationsOperatinal(String district) {
		if(district.equalsIgnoreCase("MN")) {
			return repo.getNotificationsOperatinal();
		}else {
			return repo.getNotificationsOperatinal(district);
		}
		
	}
	
	@Override
	public OperationalStatistics findById(Long operateId) {
		return repo.findOperateById(operateId);
	}
	
	@Override
	public OperationalStatistics save(OperationalStatistics operate) {
		return repo.save(operate);
	}
	
	@Override
	public List<OperationalStatistics> getOperationalByDate(LocalDate date) {
		Date d = Date.valueOf(date);
		return repo.getOperatinalByDate(d);
	}
	
	@Override
	public List<OperationalStatistics> getOperatinalInputDate(LocalDate date, Long cableTypeId) {
		Date d = Date.valueOf(date);
		return repo.getOperatinalByDateAndCableType(d, cableTypeId);		
	}
	
	@Override
	public void delete(String userUpdate, LocalDateTime updateTime, Long id) {
		repo.updateOperarionalToDelete(userUpdate, updateTime, id);
	}
	
	@Override
	public void update(String userUpdate, Long cableLinkId, Long eventId, LocalDateTime startTime,
			LocalDateTime endTime, int processingTime, Long serviceInternalId, Long service_customer_id,
			Long service_backbone_id, Long statusId, String note, LocalDateTime updateTime, Long id) {
		repo.updateOperarional(userUpdate, cableLinkId, eventId, startTime, endTime, processingTime, 
				serviceInternalId, service_customer_id, service_backbone_id, statusId, note, updateTime, id);
		
	}
	
	@Override
	public List<OperationalStatistics> getOperationalByMonth(Integer month, Integer year) {
		return repo.getOperatinalByMonth(month, year);
	}
	
	@Override
	public void runProcedureUpdateDateTime() {
		repo.procedureSuyhaocap();
	}
}
