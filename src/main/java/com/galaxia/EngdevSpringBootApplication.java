package com.galaxia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import com.galaxia.engdev.netty.NeptuneNettyServer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class EngdevSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngdevSpringBootApplication.class, args);
	}

//	private final NeptuneNettyServer tcpServer;

//	@Bean
//	public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
//		return new ApplicationListener<ApplicationReadyEvent>() {
//			@Override
//			public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//
//				tcpServer.start();
//			}
//		};
//	}
}
