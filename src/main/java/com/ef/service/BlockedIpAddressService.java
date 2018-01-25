package com.ef.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.model.BlockedIpAddress;
import com.ef.persistence.BlockedIpAddressRepository;

@Component
public class BlockedIpAddressService {

	@Autowired
	private BlockedIpAddressRepository blockedIpAddressRepository;

	@Transactional
	public Iterable<BlockedIpAddress> blockIpAddresses(final Set<String> ipsToBlock, final String comment) {
		final Set<BlockedIpAddress> blockedIpAddresses = ipsToBlock.stream()
				.map(ip -> new BlockedIpAddress(ip, comment))
				.collect(Collectors.toSet());

		return blockedIpAddressRepository.save(blockedIpAddresses);
	}

}
