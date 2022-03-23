package com.galaxia.engdev.netty;

import org.springframework.stereotype.Component;

import com.galaxia.engdev.msg.AbstractNeptuneMsg;
import com.galaxia.engdev.proc.NeptuneProc;
import com.galaxia.engdev.proc.NeptuneProcessHadler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class NeptuneMsgHandler extends SimpleChannelInboundHandler<AbstractNeptuneMsg> {

	private final NeptuneProcessHadler neptuneProcessHadler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AbstractNeptuneMsg msg) throws Exception {
		log.info(msg.toString());

		NeptuneProc proc = neptuneProcessHadler.getNeptuneProc(msg.getCommand());

		AbstractNeptuneMsg respMsg = proc.proc(msg);

		ctx.writeAndFlush(respMsg);
	}
}
