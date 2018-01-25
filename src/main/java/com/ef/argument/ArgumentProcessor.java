package com.ef.argument;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * Reads the expected parameters from the given command line args
 */
@Component
public class ArgumentProcessor {

	private static final String dateTimeFormatterPattern = "yyyy-MM-dd.HH:mm:ss";

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatterPattern);

	@Autowired
	private final ApplicationArguments applicationArguments;

	@Autowired
	ArgumentProcessor(final ApplicationArguments applicationArguments) {
		this.applicationArguments = applicationArguments;
	}

	public int getThreshold() {
		try {
			final int parsedInt = Integer.parseInt(getArgumentValue("threshold"));
			if (parsedInt < 0) {
				throw new IllegalArgumentException("Threshold argument should be a valid integer");
			}
			return parsedInt;
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException("Threshold argument should be a valid integer", e);
		}
	}

	public Duration getDuration() {
		return DurationArgument.parseDurationArg(getArgumentValue("duration")).getDuration();
	}

	public LocalDateTime getStartDate() {
		try {
			return LocalDateTime.parse(getArgumentValue("startDate"),
					dateTimeFormatter);
		} catch (final DateTimeParseException e) {
			throw new IllegalArgumentException(
					"startDate argument should be a date time in the following pattern: " + dateTimeFormatterPattern,
					e);
		}
	}

	public String getAccessLogFilePath() {
		return getArgumentValue("accesslog");
	}

	private String getArgumentValue(final String argName) {
		final List<String> argvalue = applicationArguments.getOptionValues(argName);
		if (argvalue == null || argvalue.size() != 1) {
			throw new IllegalArgumentException("argument --" + argName + " should be provided");
		}
		return argvalue.iterator().next();
	}

}
