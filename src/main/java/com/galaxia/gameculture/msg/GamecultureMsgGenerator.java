package com.galaxia.gameculture.msg;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.AbstractNeptuneMsg;
import com.galaxia.engdev.msg.NeptuneMsgGeneratorble;
import com.galaxia.engdev.msg.tag.NeptuneHeader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GamecultureMsgGenerator implements NeptuneMsgGeneratorble {

	@Override
	public AbstractNeptuneMsg getNeptuneMsg(byte[] data) {

		try {
			HashMap<NeptuneHeader, String> headerData = NeptuneMsgGeneratorble.getHeaderData(data);

			String command = headerData.get(NeptuneHeader.COMMAND);
			GamecultureCommand gemeCultureCommand = GamecultureCommand.getCommand(command);

			AbstractNeptuneMsg reqMsg = gemeCultureCommand.getMsg();

			reqMsg.setHeader(headerData);
			reqMsg.setBody(Arrays.copyOfRange(data, NeptuneHeader.getHeaderLength() - NeptuneHeader.MESSAGE_LENGTH.getLength(), data.length));

			return reqMsg;

		} catch (NeptuneException e) {
			log.error("메시지 헤더 생성 실패", e);
		}

		return null;
	}

}
