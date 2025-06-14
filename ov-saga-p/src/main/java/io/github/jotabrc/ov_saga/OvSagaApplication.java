package io.github.jotabrc.ov_saga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication @EnableAsync
public class OvSagaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvSagaApplication.class, args);
	}

}
