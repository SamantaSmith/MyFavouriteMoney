package com.example.myfavouritemoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class MyFavouriteMoneyApplication {

	//https://spring.io/guides/tutorials/spring-boot-oauth2/ - основная статья
	//https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter - статья, как обойтись без адаптера

	//https://www.baeldung.com/spring-security-5-oauth2-login - статья в баелдунге
	//Надо будет по итогам переделать security и избавиться от устаревших классов
	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		return Collections.singletonMap("name", principal.getAttribute("name"));
	}

	public static void main(String[] args) {
		SpringApplication.run(MyFavouriteMoneyApplication.class, args);
	}
}
