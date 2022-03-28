package com.galaxia.gameculture.msg.tag;

import java.util.Arrays;

import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.tag.MessageTag;
import com.galaxia.engdev.msg.tag.MessageType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GamecultureMsgTag implements MessageTag {

	AMOUNT(4, MessageType.IntegerType, "0012", "amount"), //
	PIN_NUMBER(32, MessageType.StringArray, "0008", "pinNumber"),
	ITEM_NAME(50, MessageType.StringType, "0004", "itemName"),

	;

	private int length;
	private MessageType msgType;
	private String code;
	private String name;

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public MessageType getMessageType() {
		return msgType;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	public static GamecultureMsgTag getByTagName(String tagName) {
		return Arrays.stream(values()) //
				.filter(v -> v.getName().equals(tagName)) //
				.findFirst() //
				.orElse(null); //
	}

	public static GamecultureMsgTag getByTagCode(String tagCode) {
		return Arrays.stream(values()) //
				.filter(v -> v.getCode().equals(tagCode)) //
				.findFirst() //
				.orElse(null); //
	}

	@Override
	public byte[] getByte(Object obj) throws NeptuneException {
		return this.getMessageType().getByte(obj, getCode(), getLength());
	}

}
