package com.truongsonkmhd.unetistudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UnetiStudyApplication {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("pass123"));

		SpringApplication.run(UnetiStudyApplication.class, args);
	}


}
