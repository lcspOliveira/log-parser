package com.ef.argument;

import java.time.Duration;

enum DurationArgument {

	HOURLY(Duration.ofHours(1)),
	DAILY(Duration.ofDays(1));

	private final Duration duration;

	private DurationArgument(final Duration duration) {
		this.duration = duration;
	}

	static DurationArgument parseDurationArg(final String durationArg) {
		return DurationArgument.valueOf(durationArg.toUpperCase());
	}

	Duration getDuration() {
		return this.duration;
	}

}
