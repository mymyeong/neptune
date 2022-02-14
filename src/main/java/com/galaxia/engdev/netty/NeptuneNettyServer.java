package com.galaxia.engdev.netty;

import java.net.InetSocketAddress;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class NeptuneNettyServer {

	private final ServerBootstrap serverBootstrap;

	private final InetSocketAddress tcpPort;

	private Channel serverChannel;

	public void start() {
		try {
			ChannelFuture serverChannelFuture = serverBootstrap.bind(tcpPort).sync();
			log.info("Server is started : port {}", tcpPort.getPort());
			serverChannel = serverChannelFuture.channel().closeFuture().sync().channel();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@PreDestroy
	public void stop() {
		if (serverChannel != null) {
			serverChannel.close();
			serverChannel.parent().close();
		}
	}
}
