package com.galaxia.engdev.define;

import lombok.Getter;

public enum StatusCode {

	/** 인증요청 */
	ORDER("0000"),

	/** 인증성공 */
	CERT("1001")

	;

	@Getter
	private String code;

	StatusCode(String code) {
		this.code = code;
	}
}
