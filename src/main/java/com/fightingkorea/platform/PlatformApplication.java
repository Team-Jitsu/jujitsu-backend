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

			log.info("spring.servlet.multipart.max-file-size = {}", env.getProperty("spring.servlet.multipart.max-file-size"));
			log.info("spring.servlet.multipart.max-request-size = {}", env.getProperty("spring.servlet.multipart.max-request-size"));
			log.info("server.tomcat.max-swallow-size = {}", env.getProperty("server.tomcat.max-swallow-size"));
			log.info("server.tomcat.max-http-form-post-size = {}", env.getProperty("server.tomcat.max-http-form-post-size"));
			log.info("=========================================");
		};
	}
}
