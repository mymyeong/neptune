package com.galaxia.engdev.msg.tag;

import java.util.Arrays;

import com.galaxia.engdev.exception.NeptuneException;

public enum NeptuneHeader implements MessageTag {

	// NEPTUNE HEADER
	MESSAGE_LENGTH(4, "messageLength", "messageLength", MessageType.IntegerType), //
	VERSION(10, "version", "version", MessageType.StringType), //
	SERVICE_ID(20, "serviceId", "serviceId", MessageType.StringType), //
	SERVICE_CODE(4, "serviceCode", "serviceCode", MessageType.StringType), //
	COMMAND(4, "command", "command", MessageType.StringType), //
	ORDER_ID(64, "orderId", "orderId", MessageType.StringType), //
	ORDER_DATE(14, "orderDate", "orderDate", MessageType.StringType), //
	NUMBER_OF_RECORD(4, "numberOfRecord", "numberOfRecord", MessageType.IntegerType),
	//
	;

	private int length;
	private String code;
	private String name;
	private MessageType messageType;

	private static int headerLength;
	static {
		headerLength = Arrays.stream(values()) //
				.mapToInt(v -> v.getLength()) //
				.sum();
	}

	private NeptuneHeader(int length, String code, String name, MessageType messageType) {
		this.length = length;
		this.code = code;
		this.name = name;
		this.messageType = messageType;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public MessageType getMessageType() {
		return messageType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public byte[] getByte(Object obj) throws NeptuneException {
		return ((String) obj).getBytes();
	}

	public static int getHeaderLength() {
		return headerLength;
	}
}
