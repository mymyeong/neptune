package com.galaxia.engdev.netty;

import org.springframework.stereotype.Component;

import com.galaxia.engdev.msg.NeptuneMsgGenerator;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final NeptuneMsgHandler neptuneMsgHandler;
	private final NeptuneMsgGenerator msgConvter;

	// 클라이언트 소켓 채널이 생성될 때 호출
	@Override
	protected void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();

		// decoder는 @Sharable이 안 됨, Bean 객체 주입이 안 되고, 매번 새로운 객체 생성해야 함
		NetptuneMsgDecoder decoder = new NetptuneMsgDecoder(msgConvter);

		// 뒤이어 처리할 디코더 및 핸들러 추가
		pipeline.addLast(decoder);
		pipeline.addLast(neptuneMsgHandler);
	}
}
