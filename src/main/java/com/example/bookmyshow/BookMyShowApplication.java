package com.example.bookmyshow;

import com.example.bookmyshow.controllers.UserController;
import com.example.bookmyshow.dtos.SignUpRequestDto;
import com.example.bookmyshow.dtos.SignUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookMyShowApplication implements CommandLineRunner {

	@Autowired
	private UserController userController;
	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
		signUpRequestDto.setEmailId("abc@gmail.com");
		signUpRequestDto.setPassword("xyz123");
		SignUpResponseDto signUpResponseDto = userController.signUp(signUpRequestDto);
	}
}
