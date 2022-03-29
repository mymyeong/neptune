package com.galaxia.gameculture.msg;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.galaxia.engdev.errorcode.NeptuneErrorCode;
import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.NeptuneMsg;
import com.galaxia.engdev.msg.NeptuneMsgGenerator;
import com.galaxia.engdev.msg.tag.NeptuneHeader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GamecultureMsgGenerator implements NeptuneMsgGenerator {

	@Override
	public NeptuneMsg getNeptuneMsg(byte[] data) throws NeptuneException {

		try {
			HashMap<NeptuneHeader, String> headerData = NeptuneMsgGenerator.getHeaderData(data);

			String command = headerData.get(NeptuneHeader.COMMAND);
			GamecultureCommand gemeCultureCommand = GamecultureCommand.getCommand(command);

			NeptuneMsg reqMsg = gemeCultureCommand.getMsg();

			reqMsg.setHeader(headerData);
			reqMsg.setBody(Arrays.copyOfRange(data, NeptuneHeader.getHeaderLength(), data.length));

			return reqMsg;

		} catch (NeptuneException e) {
			log.error("메시지 헤더 생성 실패", e);
			throw new NeptuneException(NeptuneErrorCode.MESSAGE_PASSING_ERROR);
		}
	}

}
