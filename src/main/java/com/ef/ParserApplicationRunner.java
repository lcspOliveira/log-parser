package com.ef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ef.argument.ArgumentProcessor;
import com.ef.model.AccessLog;
import com.ef.parser.AccessLogParser;
import com.ef.service.AccessLogService;
import com.ef.service.BlockedIpAddressService;

@SpringBootApplication(scanBasePackageClasses = Parser.class)
public class ParserApplicationRunner implements ApplicationRunner {

	final Logger logger = LogManager.getLogger("RequiredMessages");

	@Autowired
	private AccessLogService accessLogService;
	@Autowired
	private BlockedIpAddressService blockedIpAddressService;

	@Autowired
	private ArgumentProcessor argumentProcessor;

	@Override
	public void run(final ApplicationArguments args) throws Exception {

		final LocalDateTime startDateTime = argumentProcessor.getStartDate();
		final LocalDateTime endDateTime = startDateTime.plus(argumentProcessor.getDuration());
		final int threshold = argumentProcessor.getThreshold();

		tryLoadLogEntriesToMySql();

		final Set<String> ipsToBlock = accessLogService.findAllIpAddressesExcedingThreshold(
				startDateTime, endDateTime, threshold);

		if (ipsToBlock.isEmpty()) {
			logger.info("No IPs to block. Shutting down...");
			return;
		}

		logger.info("The following ips will be blocked:");
		ipsToBlock.stream().forEach(ip -> logger.info(ip));

		final String blockComment = String.format(
				"Blocked for exceeding request threshold of %d requests between %s and %s",
				threshold,
				startDateTime,
				endDateTime);

		blockedIpAddressService.blockIpAddresses(ipsToBlock,
				blockComment);

		logger.info("Ips blocked successfully. Shutting down...");
	}

	private void tryLoadLogEntriesToMySql() {
		try (final InputStream inputStream = getLogFileAsStream()) {
			if (inputStream != null) {
				logger.info("accesslog file found. access logs will be parsed...");
				final List<AccessLog> logs = AccessLogParser.parse(inputStream);
				logger.info(String.format("Found %d log entries... loading entries to MySql..", logs.size()));
				accessLogService.save(logs);
				logger.info("Entries successfully loaded to MySql. Proceding to request threshold test...");
				return;
			}
		} catch (final IOException e) {
		}
		logger.info("No accesslog could be read. Proceding to request threshold test...");
	}

	private InputStream getLogFileAsStream() throws FileNotFoundException {
		final String logFilePath = argumentProcessor.getAccessLogFilePath();
		final File file = new File(logFilePath);
		if (file.exists() && file.isFile() && file.canRead()) {
			return new FileInputStream(file);
		}
		// try to read the file from classpath
		return getClass().getClassLoader().getResourceAsStream(logFilePath);
	}

}
