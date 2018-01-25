package com.ef.argument;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;

@RunWith(MockitoJUnitRunner.class)
public class ArgumentParserTest {

	private ArgumentProcessor subject;

	@Mock
	private ApplicationArguments argumentsMock;

	@Before
	public void setup() {
		subject = new ArgumentProcessor(argumentsMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenStartNotPresent() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString())).thenReturn(null);
		subject.getStartDate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenDurationNotPresent() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString())).thenReturn(null);
		subject.getDuration();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenThresholdNotPresent() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString())).thenReturn(null);
		subject.getThreshold();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenThresholdIsEmpty() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString())).thenReturn(Collections.emptyList());
		subject.getThreshold();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenStartIsEmpty() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString())).thenReturn(Collections.emptyList());
		subject.getStartDate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenDurationIsEmpty() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString())).thenReturn(Collections.emptyList());
		subject.getDuration();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenDurationIsInvalid() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString()))
				.thenReturn(Collections.singletonList("yearly"));
		subject.getDuration();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenStartIsInvalid() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString()))
				.thenReturn(Collections.singletonList("12 jan 1018 - 12:14"));
		subject.getStartDate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenThresholdIsInvalid() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString()))
				.thenReturn(Collections.singletonList("not a number"));
		subject.getThreshold();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldTrhowIllegalArgumentWhenThresholdIsNegative() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString()))
				.thenReturn(Collections.singletonList("-1"));
		subject.getThreshold();
	}

	@Test
	public void shouldReturnCorrectValueWhenThresholdIsOk() {
		IntStream.of(0, 100, 200).forEach(n -> {
			Mockito.when(argumentsMock.getOptionValues(Mockito.anyString()))
					.thenReturn(Collections.singletonList(Integer.toString(n)));
			assertEquals(subject.getThreshold(), n);
		});

	}

	@Test
	public void shouldReturnCorrectValueWhenStartDateIsOk() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString()))
				.thenReturn(Collections.singletonList("2017-01-01.00:00:00"));
		final LocalDateTime result = subject.getStartDate();
		assertEquals(result.getYear(), 2017);
		assertEquals(result.getMonth(), Month.JANUARY);
		assertEquals(result.getDayOfYear(), 01);
		assertEquals(result.getHour(), 00);
		assertEquals(result.getMinute(), 00);
		assertEquals(result.getSecond(), 00);
	}

	@Test
	public void shouldReturnCorrectValueWhenDurationIsHourly() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString()))
				.thenReturn(Collections.singletonList("hourly"));
		assertEquals(subject.getDuration().getSeconds(), 60 * 60);
	}

	@Test
	public void shouldReturnCorrectValueWhenDurationIsDaily() {
		Mockito.when(argumentsMock.getOptionValues(Mockito.anyString()))
				.thenReturn(Collections.singletonList("daily"));
		assertEquals(subject.getDuration().getSeconds(), 24 * 60 * 60);
	}

}
