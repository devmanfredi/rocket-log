package com.rocketlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EntityScan(basePackageClasses = {RocketLogApplication.class, Jsr310Converters.class})
@EnableJpaAuditing
public class RocketLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketLogApplication.class, args);
	}

}
