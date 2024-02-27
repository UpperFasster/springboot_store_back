package com.UpperFasster.Magazine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MagazineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagazineApplication.class, args);
	}

}
