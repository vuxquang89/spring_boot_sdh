package vux.codejava.repository.operate;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import vux.codejava.entity.operate.OperationalStatistics;

public interface OperationalStatisticsRepository extends JpaRepository<OperationalStatistics, Long>{
	
	//MySQL action => 0 : delete; 1 : view
	
	@Query("SELECT o FROM operational_statistics o WHERE o.id = ?1 AND o.action = 1")
	OperationalStatistics findOperateById(Long operateId);
	
	@Query("SELECT o FROM operational_statistics o WHERE DATE(o.startTime) = ?1 AND o.action = 1")
	List<OperationalStatistics> getOperatinalByDate(Date date);
	
	@Query("SELECT o FROM operational_statistics o "
			+ "JOIN cable_link cl ON cl.id = o.cableLink.id "
			+ "JOIN cable_type ct ON ct.id = cl.cableType.id "
			+ "WHERE DATE(o.startTime) = ?1 AND ct.id = ?2 AND cl.id != 0 AND o.action = 1")
	List<OperationalStatistics> getOperatinalByDateAndCableType(Date date, Long cableTypeId);
	
	@Query("SELECT o FROM operational_statistics o WHERE MONTH(o.startTime) = ?1 AND YEAR(o.startTime) = ?2 AND o.action = 1")
	List<OperationalStatistics> getOperatinalByMonth(Integer month, Integer year);
	
	@Query("SELECT o FROM operational_statistics o WHERE o.district = ?1 AND o.event.id = 9 AND o.status.id != 3 AND o.action = 1 ORDER BY o.startTime DESC")
	List<OperationalStatistics> getNotificationsOperatinal(String district);
	
	@Query("SELECT o FROM operational_statistics o WHERE o.event.id = 9 AND o.status.id != 3 AND o.action = 1 ORDER BY o.startTime DESC")
	List<OperationalStatistics> getNotificationsOperatinal();
	
	@Transactional
	@Modifying
	@Query(value="UPDATE operational_statistics SET user_update = ?1, update_time = ?2, action = 0 WHERE id = ?3", nativeQuery = true)
	void updateOperarionalToDelete(String userUpdate, LocalDateTime updateTime, Long id);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE operational_statistics SET user_update = ?1, cable_link_id = ?2, event_id = ?3, start_time = ?4, end_time = ?5,"
			+ " processing_time = ?6, service_internal_id = ?7, service_customer_id = ?8, service_backbone_id = ?9,"
			+ " status_id = ?10, note = ?11, update_time = ?12 WHERE id = ?13", nativeQuery = true)
	void updateOperarional(String userUpdate, Long cableLinkId, Long eventId, LocalDateTime startTime, LocalDateTime endTime,
			int processingTime, Long serviceInternalId, Long service_customer_id, Long service_backbone_id, Long statusId,
			String note, LocalDateTime updateTime, Long id);
	
	
	@Procedure("suyhaocap_procedure")
	void procedureSuyhaocap();
	
}
