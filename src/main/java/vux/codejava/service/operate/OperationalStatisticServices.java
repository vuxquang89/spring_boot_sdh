package vux.codejava.service.operate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import vux.codejava.entity.operate.OperationalStatistics;

public interface OperationalStatisticServices {

	OperationalStatistics save(OperationalStatistics operate);
	
	List<OperationalStatistics> findAll();
	
	List<OperationalStatistics> getNotificationsOperatinal(String district);
	
	void delete(String userUpdate, LocalDateTime updateTime, Long id);
	
	void update(String userUpdate, Long cableLinkId, Long eventId, LocalDateTime startTime, LocalDateTime endTime,
			int processingTime, Long serviceInternalId, Long service_customer_id, Long service_backbone_id, Long statusId,
			String note, LocalDateTime updateTime, Long id);
	
	OperationalStatistics findById(Long operateId);
	
	List<OperationalStatistics> getOperationalByDate(LocalDate date);
	
	List<OperationalStatistics> getOperationalByMonth(Integer month, Integer year);
	
	List<OperationalStatistics> getOperatinalInputDate(LocalDate date, Long cableTypeId);
	
	void runProcedureUpdateDateTime();
}
