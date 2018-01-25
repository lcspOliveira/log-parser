package com.ef.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ef.model.AccessLog;

public interface AccessLogRepository extends CrudRepository<AccessLog, Integer> {

	@Query("select log.ipAddress "
			+ "from AccessLog log "
			+ "where log.dateTime between :startTime and :endTime "
			+ "group by log.ipAddress "
			+ "having count(log) > :threshold")
	List<String> findAllIpAddressesExcedingThreshold(
			@Param("startTime") final LocalDateTime startTime,
			@Param("endTime") final LocalDateTime endTime,
			@Param("threshold") final long threshold);

}
