package com.galaxia.engdev.msg.tag;

import java.util.Arrays;

import com.galaxia.engdev.exception.NeptuneException;

public enum NeptuneHeader implements MessageTag {

	// NEPTUNE HEADER
	MESSAGE_LENGTH(4, "messageLength", "messageLength", MessageType.Integer), //
	VERSION(10, "version", "version", MessageType.String), //
	SERVICE_ID(20, "serviceId", "serviceId", MessageType.String), //
	SERVICE_CODE(4, "serviceCode", "serviceCode", MessageType.String), //
	COMMAND(4, "command", "command", MessageType.String), //
	ORDER_ID(64, "orderId", "orderId", MessageType.String), //
	ORDER_DATE(14, "orderDate", "orderDate", MessageType.String), //
	NUMBER_OF_RECORD(4, "numberOfRecord", "numberOfRecord", MessageType.Integer),
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
