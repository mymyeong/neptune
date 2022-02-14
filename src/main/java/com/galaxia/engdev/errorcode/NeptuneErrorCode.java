package com.galaxia.engdev.errorcode;

/**
 * 에러 코드 정의
 *
 * @author mjhan
 *
 */
public class NeptuneErrorCode {

	/** 정상 응답(0000-00) */
	public static final String SUCCESS = "SUCCESS";
//	SUCCESS(),

	/** 알수 없는 에러 */
	public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
//	UNKNOWN_ERROR(),

	/** 내부에러코드 조회 실패 */
	public static final String INTERNAL_ERROR_CODE = "INTERNAL_ERROR_CODE";
//	INTERNAL_ERROR_CODE(),

	// 요청 메시지 에러
	/** 메시지 길이 읽기 오류 */
	public static final String READ_LENGTH_ERROR = "READ_LENGTH_ERROR";

	/** 메시지 처리객체 생성 실패 */
	public static final String PROCESS_INIT_ERROR = "PROCESS_INIT_ERROR";

	/** 메시지 생성 실패 */
	public static final String MESSAGE_CREATE_FAIL = "MESSAGE_CREATE_FAIL";

	/** 메시지 파싱 실패 */
	public static final String MESSAGE_PASSING_ERROR = "MESSAGE_PASSING_ERROR";

	// 필수값 체크 에러
	/** SERVICE_ID 누락 에러 */
	public static final String INVALID_SERVICE_ID_FIELD = "INVALID_SERVICE_ID_FIELD";

	/** ORDER_ID 누락 에러 */
	public static final String INVALID_ORDER_ID_FIELD = "INVALID_ORDER_ID_FIELD";

	/** ORDER_DATE 누락 에러 */
	public static final String INVALID_ORDER_DATE_FIELD = "INVALID_ORDER_DATE_FIELD";

	/** 메시지 복호화 에러 */
	public static final String MESSAGE_DECRYPT_ERROR = "MESSAGE_DECRYPT_ERROR";
	
	/** 메시지 테그 타입 에러 */
	public static final String MESSAGE_TAG_TYPE_ERROR = "TAG_TYPE_ERROR";

}
