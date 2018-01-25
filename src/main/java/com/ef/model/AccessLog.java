package com.ef.model;

import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class AccessLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column
	private final LocalDateTime dateTime;
	@Column
	private final String ipAddress;
	@Column
	private final String request;
	@Column
	private final int status;
	@Column
	private final String userAgent;

	public AccessLog(final LocalDateTime dateTime,
			final String ipAddress,
			final String request,
			final int status,
			final String userAgent) {
		this.dateTime = dateTime;
		this.ipAddress = ipAddress;
		this.request = request;
		this.status = status;
		this.userAgent = userAgent;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getRequest() {
		return request;
	}

	public int getStatus() {
		return status;
	}

	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public String toString() {
		return "WebLog [dateTime=" + dateTime + ", ipAddress=" + ipAddress + ", request=" + request + ", status="
				+ status + ", userAgent=" + userAgent + "]";
	}

	@Override
	public int hashCode() {
		if (id == null)
			return super.hashCode();
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
		final AccessLog other = (AccessLog) obj;
		return id != null && id.equals(other.id);
	}

}
