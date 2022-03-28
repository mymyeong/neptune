package com.galaxia.engdev.errorcode;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "responseMsg", "detailMsg" }) // 에러코드가 같다면 같은 에러코드로 인정
public class ErrorCode {

	public ErrorCode(ErrorCode errorCode) {
		this.responseCode = errorCode.responseCode;
		this.responseMsg = errorCode.responseMsg;
		this.detailCode = errorCode.detailCode;
		this.detailMsg = errorCode.detailMsg;
	}

	private String responseCode;
	private String responseMsg;
	private String detailCode;
	private String detailMsg;

}
