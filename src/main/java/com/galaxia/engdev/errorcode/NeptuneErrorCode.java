package com.galaxia.engdev.errorcode;

/**
 * 에러 코드 정의
 *
 * @author mjhan
 *
 */
public class NeptuneErrorCode {
	// TODO : 에러코드 현행화 확인 필요

	/** 정상 응답(0000-00) */
	public static final ErrorCode SUCCESS = new ErrorCode("0000", "성공", "00", "정상처리되었습니다.");

	/** 알수 없는 에러 */
	public static final ErrorCode UNKNOWN_ERROR = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "알 수 없는 오류가 발생하였습니다.");

	/** 내부에러코드 조회 실패 */
	public static final ErrorCode INTERNAL_ERROR_CODE = new ErrorCode("9999", "내부에러코드 조회 실패", "00", "내부에러코드 조회 실패");

	// 요청 메시지 에러
	/** 메시지 길이 읽기 오류 */
	public static final ErrorCode READ_LENGTH_ERROR = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "메시지 길이 읽기 오류");

	/** 메시지 처리객체 생성 실패 */
	public static final ErrorCode PROCESS_INIT_ERROR = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "메시지 처리객체 생성 실패");

	/** 메시지 생성 실패 */
	public static final ErrorCode MESSAGE_CREATE_FAIL = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "메시지 생성 실패");

	/** 메시지 파싱 실패 */
	public static final ErrorCode MESSAGE_PASSING_ERROR = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "메시지 파싱 실패");

	// 필수값 체크 에러
	/** SERVICE_ID 누락 에러 */
	public static final ErrorCode INVALID_SERVICE_ID_FIELD = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "SERVICE_ID 누락");

	/** ORDER_ID 누락 에러 */
	public static final ErrorCode INVALID_ORDER_ID_FIELD = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "ORDER_ID 누락");

	/** ORDER_DATE 누락 에러 */
	public static final ErrorCode INVALID_ORDER_DATE_FIELD = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "ORDER_DATE 누락");

	/** 메시지 복호화 에러 */
	public static final ErrorCode MESSAGE_DECRYPT_ERROR = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "메시지 복호화 실패");

	/** 메시지 테그 타입 에러 */
	public static final ErrorCode MESSAGE_TAG_TYPE_ERROR = new ErrorCode("9999", "알 수 없는 오류 발생", "00", "요청 데이터 타입 오류");

}
