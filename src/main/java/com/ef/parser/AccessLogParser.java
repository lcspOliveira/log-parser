package com.ef.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.ef.model.AccessLog;

public class AccessLogParser {

	// 2017-01-01 00:00:11.763|192.168.234.82|"GET / HTTP/1.1"|200|"swcd (unknown
	// version) CFNetwork/808.2.16 Darwin/15.6.0"

	private static final String SEPARATOR = "\\|";

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public static List<AccessLog> parse(final InputStream inputStream) {
		return parse(new BufferedReader(new InputStreamReader(inputStream)));
	}

	public static List<AccessLog> parse(final BufferedReader reader) {
		return reader.lines()
				.parallel()
				.map(line -> parseLine(line))
				.collect(Collectors.toList());
	}

	private static AccessLog parseLine(final String line) {
		final String[] splitted = line.split(SEPARATOR);
		return new AccessLog(
				LocalDateTime.parse(splitted[0], dateTimeFormatter),
				splitted[1], splitted[2],
				Integer.parseInt(splitted[3]),
				splitted[4]);
	}

}
