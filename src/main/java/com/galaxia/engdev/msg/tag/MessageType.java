package com.galaxia.engdev.msg.tag;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import com.galaxia.engdev.msg.AbstractNeptuneMsg;
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

		@Override
		public boolean isArray() {
			return true;
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
			if (obj instanceof String) {
				return true;
			}
			return false;
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

		@Override
		public boolean isArray() {
			return true;
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

	public boolean isArray() {
		return false;
	}

	public byte[] getByte(Object obj, String tagCode, int length) {
		if (isArray()) {
			return getArrayByte(obj, tagCode, length);
		} else {
			return getObjctByte(obj, tagCode, length);
		}
	}

	protected byte[] getArrayByte(Object obj, String tagCode, int length) {
		int dataCnt = 0, totalLength = 0;
		ByteBuffer buffer;
		byte[] data = null;
		LinkedList<byte[]> dataList = new LinkedList<>();

		if (this.equals(MessageType.IntArray)) {
			int[] temp = (int[]) obj;
			dataCnt = temp.length;
			for (int i = 0; i < dataCnt; i++) {
				data = NumberUtil.toZeroString(temp[i], length).getBytes();
				dataList.add(data);
				totalLength += data.length;
			}
		} else {
			String[] temp = (String[]) obj;
			dataCnt = temp.length;
			for (int i = 0; i < dataCnt; i++) {
				data = StringUtil.stringToFillNullByte(temp[i].toString(), length);
				dataList.add(data);
				totalLength += data.length;
			}
		}

		int bodyDataLength = totalLength + (AbstractNeptuneMsg.VALUE_LENGTH * dataCnt);

		buffer = ByteBuffer.allocate(AbstractNeptuneMsg.MESSAGE_TAG_LENGTH //
				+ AbstractNeptuneMsg.MESSAGE_COUNT_LENGTH //
				+ AbstractNeptuneMsg.VALUE_LENGTH //
				+ bodyDataLength);

		buffer.put(tagCode.getBytes())//
				.put(NumberUtil.toZeroString(dataCnt, AbstractNeptuneMsg.MESSAGE_COUNT_LENGTH).getBytes())//
				.put(NumberUtil.toZeroString(totalLength, AbstractNeptuneMsg.VALUE_LENGTH).getBytes());

		for (byte[] d : dataList) {
			buffer.put(NumberUtil.toZeroString(d.length, AbstractNeptuneMsg.VALUE_LENGTH).getBytes())//
					.put(d);
		}

		return buffer.array();
	}

	protected byte[] getObjctByte(Object obj, String tagCode, int length) {
		int dataCnt = 0, totalLength = 0;
		ByteBuffer buffer;
		byte[] data = null;
		if (this.equals(MessageType.Integer)) {
			data = NumberUtil.toZeroString(obj.toString(), length).getBytes();
		} else {
			data = StringUtil.stringToFillSpaceByte(obj.toString(), length);
		}

		buffer = ByteBuffer.allocate(AbstractNeptuneMsg.MESSAGE_TAG_LENGTH //
				+ AbstractNeptuneMsg.MESSAGE_COUNT_LENGTH //
				+ AbstractNeptuneMsg.VALUE_LENGTH //
				+ AbstractNeptuneMsg.VALUE_LENGTH //
				+ data.length);

		dataCnt = 1;
		totalLength = data.length + AbstractNeptuneMsg.VALUE_LENGTH;

		buffer.put(tagCode.getBytes())//
				.put(NumberUtil.toZeroString(dataCnt, AbstractNeptuneMsg.MESSAGE_COUNT_LENGTH).getBytes())//
				.put(NumberUtil.toZeroString(totalLength, AbstractNeptuneMsg.VALUE_LENGTH).getBytes())//
				.put(NumberUtil.toZeroString(data.length, AbstractNeptuneMsg.VALUE_LENGTH).getBytes())//
				.put(data);

		return buffer.array();
	}

}
