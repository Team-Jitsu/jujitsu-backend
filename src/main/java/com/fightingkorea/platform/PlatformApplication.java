package com.fightingkorea.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlatformApplication {

	private static final Logger log = LoggerFactory.getLogger(PlatformApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(Environment env) {
		return (ApplicationArguments args) -> {
			log.info("====== Environment Variables Loaded ======");
			log.info("spring.datasource.username = {}", env.getProperty("spring.datasource.username"));
			log.info("spring.datasource.password = {}", env.getProperty("spring.datasource.password"));
			log.info("app.s3.bucket = {}", env.getProperty("app.s3.bucket"));
			log.info("jwt.access.secret.key = {}", env.getProperty("jwt.access.secret.key"));
			log.info("jwt.refresh.secret.key = {}", env.getProperty("jwt.refresh.secret.key"));
			log.info("toss.payments.secret.key = {}", env.getProperty("toss.payments.secret.key"));
			log.info("=========================================");
		};
	}
}
