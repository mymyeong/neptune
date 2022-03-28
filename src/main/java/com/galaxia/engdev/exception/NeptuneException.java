package com.galaxia.engdev.exception;

import com.galaxia.engdev.errorcode.ErrorCode;

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

	private ErrorCode neptuneErrorCode;

}
