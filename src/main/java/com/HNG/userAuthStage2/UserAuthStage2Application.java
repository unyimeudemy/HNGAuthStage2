package com.HNG.userAuthStage2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserAuthStage2Application {

	public static void main(String[] args) {
		System.getenv("DB_USERNAME");
		SpringApplication.run(UserAuthStage2Application.class, args);
	}

}


