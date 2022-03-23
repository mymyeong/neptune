package com.galaxia.engdev.netty;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartupTask implements ApplicationListener<ApplicationReadyEvent> {

	private final NeptuneNettyServer nettyServerSocket;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		log.info("ApplicationStartupTask>onApplicationEvent");

		nettyServerSocket.start();
	}
}
