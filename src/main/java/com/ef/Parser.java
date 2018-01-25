package com.ef;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

public class Parser {

	public static void main(final String[] args) {

		final SpringApplication app = new SpringApplication(ParserApplicationRunner.class);
		// don't show spring logo
		app.setBannerMode(Banner.Mode.OFF);
		// loads hibernate dialect for MySql
		app.setAdditionalProfiles("default");
		app.run(args);

	}

}
