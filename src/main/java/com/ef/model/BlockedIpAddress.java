package com.ef.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class BlockedIpAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column
	private final String IpAddress;

	@Column
	private final String comment;

	public BlockedIpAddress(final String ipAddress, final String comment) {
		super();
		IpAddress = ipAddress;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public String getIpAddress() {
		return IpAddress;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return "BlockedIpAddress [id=" + id + ", IpAddress=" + IpAddress + ", comment=" + comment + "]";
	}

	@Override
	public int hashCode() {
		if (id == null) {
			return super.hashCode();
		}
		return id.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BlockedIpAddress other = (BlockedIpAddress) obj;
		return id != null && id.equals(other.id);
	}

}
