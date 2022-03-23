package com.galaxia.engdev.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * telecomGw Exception
 *
 * @author mjhan
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NeptuneException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -908627710442109259L;

	private String errorCode;

}
