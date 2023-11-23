package com.infyfacebook.friendrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FriendRequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendRequestApplication.class, args);
	}
	@Bean
//	@LoadBalanced
    RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
