package com.galaxia.engdev.netty;

import java.util.List;

import org.springframework.stereotype.Component;

import com.galaxia.engdev.msg.NeptuneMsgGeneratorble;
import com.galaxia.engdev.msg.tag.NeptuneHeader;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NetptuneMsgDecoder extends ByteToMessageDecoder {

	private final NeptuneMsgGeneratorble msgConvter;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("NetptuneMsgDecoder call decode");

//		if (in.readableBytes() < NeptuneHeader.MESSAGE_LENGTH.getLength()) {
//			return;
//		}

		byte[] dataLengthByte = new byte[NeptuneHeader.MESSAGE_LENGTH.getLength()];
		in.readBytes(dataLengthByte);
		int dataLength = Integer.parseInt(new String(dataLengthByte));
		if (in.readableBytes() < dataLength) {
			return;
		}

		byte[] readMsgByte = new byte[dataLength];
		in.readBytes(readMsgByte);

		out.add(msgConvter.getNeptuneMsg(readMsgByte));
	}

}
