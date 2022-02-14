package com.galaxia.engdev.dexeption;

/**
 * telecomGw Exception
 *
 * @author mjhan
 *
 */
public class NeptuneException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -908627710442109259L;

	private String errorCode;

	public NeptuneException() {
	}

	public NeptuneException(String errorException) {
		this.errorCode = errorException;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
