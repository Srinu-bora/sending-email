package com.example.emailapp;

import org.springframework.boot.SpringApplication;

public class TestEmailappApplication {

	public static void main(String[] args) {
		SpringApplication.from(EmailappApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
