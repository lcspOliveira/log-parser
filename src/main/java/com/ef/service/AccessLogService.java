package com.ef.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.model.AccessLog;
import com.ef.model.BlockedIpAddress;
import com.ef.persistence.AccessLogRepository;
import com.ef.persistence.BlockedIpAddressRepository;

@Component
public class AccessLogService {

	@Autowired
	private AccessLogRepository webLogRepository;


	@Transactional
	public void save(final Iterable<AccessLog> logs) {
		webLogRepository.save(logs);
	}

	@Transactional
	public Set<String> findAllIpAddressesExcedingThreshold(
			final LocalDateTime startTime,
			final LocalDateTime endTime,
			final int threshold) {
		return webLogRepository
				.findAllIpAddressesExcedingThreshold(startTime, endTime, threshold)
				.stream().map(ipAddress -> ipAddress)
				.collect(Collectors.toSet());
	}



}
