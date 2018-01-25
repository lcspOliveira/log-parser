package com.ef.persistence;

import org.springframework.data.repository.CrudRepository;

import com.ef.model.BlockedIpAddress;

public interface BlockedIpAddressRepository extends CrudRepository<BlockedIpAddress, Integer> {

}
