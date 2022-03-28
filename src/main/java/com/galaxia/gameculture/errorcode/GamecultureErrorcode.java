package com.galaxia.gameculture.errorcode;

import com.galaxia.engdev.errorcode.NeptuneErrorCode;
import com.galaxia.engdev.errorcode.ErrorCode;

public class GamecultureErrorcode extends NeptuneErrorCode {

	/** 요청 메시지 타입 에러 */
	public static final ErrorCode REQ_MESSAGE_TYPE_ERROR = new ErrorCode("1100", "요청 메시지 타입 오류", "01", "요청 메시지 타입이  잘못되었습니다.");
}
