package com.galaxia.engdev.message.tag;

import com.galaxia.engdev.util.NumberUtil;
import com.galaxia.engdev.util.StringUtil;

/**
 * 메시지 타입
 *
 * @author mjhan
 *
 */
public enum MessageType {
	/**
	 * 정수 기본형
	 *
	 * @author mjhan
	 *
	 */
	Integer() {

		@Override
		public boolean typeChecker(Object obj) {

			if (NumberUtil.isNumber(obj.toString())) {
				return true;
			}
			return false;
		}
	},

	/**
	 * 정수 배열
	 *
	 * @author mjhan
	 *
	 */
	IntArray() {
		@Override
		public boolean typeChecker(Object obj) {
			if (obj instanceof int[]) {
				return true;
			}
			return false;
		}
	},

	/**
	 * 정수 배열
	 *
	 * @author mjhan
	 *
	 */
	IntegerArray() {
		@Override
		public boolean typeChecker(Object obj) {
			if (obj instanceof Integer[]) {
				return true;
			}
			return false;
		}
	},

	/**
	 * 문자열
	 *
	 * @author mjhan
	 *
	 */
	String() {
		@Override
		public boolean typeChecker(Object obj) {
			return true;
		}
	},

	/**
	 * 문자열 배열
	 *
	 * @author mjhan
	 *
	 */
	StringArray() {
		@Override
		public boolean typeChecker(Object obj) {
			if (obj instanceof String[]) {
				return true;
			}
			return false;
		}
	},

	/**
	 * Y(y) 또는 N(n)
	 *
	 * @author mjhan
	 *
	 */
	Yn() {
		@Override
		public boolean typeChecker(Object obj) {
			if (obj.toString().equalsIgnoreCase("Y") || obj.toString().equalsIgnoreCase("N")) {
				return true;
			}
			return false;
		}
	},

	/**
	 * IPv4 String
	 *
	 * @author mjhan
	 *
	 */
	IpAddressV4() {
		@Override
		public boolean typeChecker(Object obj) {
			return StringUtil.isIpAddressV4(obj.toString());
		}
	},

	;

	/**
	 * Obj의 형태가 Type 객체에 맞는지
	 *
	 * @param obj
	 * @return
	 */
	public abstract boolean typeChecker(Object obj);

}
