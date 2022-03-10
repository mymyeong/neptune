package com.galaxia.engdev.netty;

import org.springframework.stereotype.Component;

import com.galaxia.engdev.message.AbstractNeptuneMsg;
import com.galaxia.engdev.proc.AbstractNeptuneProc;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class NeptuneMsgHandler extends SimpleChannelInboundHandler<AbstractNeptuneMsg> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AbstractNeptuneMsg msg) throws Exception {
		log.info(msg.getLogMessage());

		AbstractNeptuneMsg respMsg = AbstractNeptuneProc.proc(msg);

		ctx.writeAndFlush(respMsg);
	}

	/*
	 * // private ByteBuf buffer; // private int totalSize = 0;
	 *
	 * @Override public void channelActive(ChannelHandlerContext ctx) {
	 *
	 * ctx.fireChannelActive(); if (log.isDebugEnabled()) {
	 * log.debug(ctx.channel().remoteAddress() + ""); } String remoteAddress =
	 * ctx.channel().remoteAddress().toString();
	 *
	 * ctx.writeAndFlush("Your remote address is " + remoteAddress +
	 * System.lineSeparator());
	 *
	 * }
	 *
	 * @Override public void channelRead(ChannelHandlerContext ctx, Object msg) { //
	 * String stringMessage = (String) msg; // log.info(stringMessage); //
	 * ctx.channel().writeAndFlush(stringMessage + System.lineSeparator());
	 *
	 * // ByteBuf b = (ByteBuf) msg; // buffer.writeBytes(b); // b.release(); // int
	 * readableByte = buffer.readableBytes(); // if (readableByte > 12 && totalSize
	 * == 0) { // byte[] bytes = new byte[4]; // buffer.getBytes(4, bytes); ////
	 * this.totalSize = CommonUtils.bytesToInt(bytes); // this.totalSize = new
	 * BigInteger(bytes).intValue(); // } // if (readableByte == totalSize) { //
	 * byte[] bytes = new byte[buffer.readableBytes()]; // buffer.readBytes(bytes);
	 * //// PacketDto packet =
	 * PacketDto.builder().bytes(bytes).filePath(commonProp.getFilePath()).build();
	 * //// analyzeSched.addQueue(packet); // clearBuffer(); // }
	 *
	 * }
	 *
	 * @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable
	 * cause) { log.error(cause.getMessage(), cause); }
	 *
	 * // @Override // public void handlerAdded(ChannelHandlerContext ctx) throws
	 * Exception { // buffer = ctx.alloc().buffer(); // } // // @Override // public
	 * void handlerRemoved(ChannelHandlerContext ctx) throws Exception { //
	 * clearBuffer(); // } // // private void clearBuffer() { // if (buffer != null)
	 * { // buffer.release(); // buffer = null; // } // }
	 */
}
