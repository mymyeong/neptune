package com.galaxia.engdev.message.tag;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import com.galaxia.engdev.dexeption.NeptuneException;
import com.galaxia.engdev.message.AbstractNeptuneMsg;
import com.galaxia.engdev.util.NumberUtil;
import com.galaxia.engdev.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NeptuneMsgTag implements IMessageTag {

	private String code;
	private String name;
	private int length;
	private MessageType messageType;

	@Override
	public MessageType getMessageType() {
		return messageType;
	}

	@Override
	public byte[] getByte(Object obj) throws NeptuneException {
		switch (messageType) {
		case IntArray:
		case StringArray:
			return getArrayByte(obj);

		case Integer:
		case String:
		case Yn:
		case IpAddressV4:
		default:
			return getObjctByte(obj);
		}
	}

	protected byte[] getArrayByte(Object obj) {
		int dataCnt = 0, totalLength = 0;
		ByteBuffer buffer;
		byte[] data = null;
		LinkedList<byte[]> dataList = new LinkedList<>();

		if (getMessageType().equals(MessageType.IntArray)) {
			int[] temp = (int[]) obj;
			dataCnt = temp.length;
			for (int i = 0; i < dataCnt; i++) {
				data = NumberUtil.toZeroString(temp[i], this.getLength()).getBytes();
				dataList.add(data);
				totalLength += data.length;
			}
		} else {
			String[] temp = (String[]) obj;
			dataCnt = temp.length;
			for (int i = 0; i < dataCnt; i++) {
				data = StringUtil.stringToFillNullByte(temp[i].toString(), this.getLength());
				dataList.add(data);
				totalLength += data.length;
			}
		}

		int bodyDataLength = totalLength + (AbstractNeptuneMsg.VALUE_LENGTH * dataCnt);

		buffer = ByteBuffer.allocate(AbstractNeptuneMsg.MESSAGE_TAG_LENGTH //
				+ AbstractNeptuneMsg.MESSAGE_COUNT_LENGTH //
				+ AbstractNeptuneMsg.VALUE_LENGTH //
				+ bodyDataLength);

		buffer.put(getName().getBytes())//
				.put(NumberUtil.toZeroString(dataCnt, AbstractNeptuneMsg.MESSAGE_COUNT_LENGTH).getBytes())//
				.put(NumberUtil.toZeroString(totalLength, AbstractNeptuneMsg.VALUE_LENGTH).getBytes());

		for (byte[] d : dataList) {
			buffer.put(NumberUtil.toZeroString(d.length, AbstractNeptuneMsg.VALUE_LENGTH).getBytes())//
					.put(d);
		}

		return buffer.array();
	}

	protected byte[] getObjctByte(Object obj) {
		int dataCnt = 0, totalLength = 0;
		ByteBuffer buffer;
		byte[] data = null;
		if (getMessageType().equals(MessageType.Integer)) {
			data = NumberUtil.toZeroString(obj.toString(), this.getLength()).getBytes();
		} else {
			data = StringUtil.stringToFillSpaceByte(obj.toString(), this.getLength());
		}

		buffer = ByteBuffer.allocate(AbstractNeptuneMsg.MESSAGE_TAG_LENGTH //
				+ AbstractNeptuneMsg.MESSAGE_COUNT_LENGTH //
				+ AbstractNeptuneMsg.VALUE_LENGTH //
				+ AbstractNeptuneMsg.VALUE_LENGTH //
				+ data.length);

		dataCnt = 1;
		totalLength = data.length + AbstractNeptuneMsg.VALUE_LENGTH;

		buffer.put(getCode().getBytes())//
				.put(NumberUtil.toZeroString(dataCnt, AbstractNeptuneMsg.MESSAGE_COUNT_LENGTH).getBytes())//
				.put(NumberUtil.toZeroString(totalLength, AbstractNeptuneMsg.VALUE_LENGTH).getBytes())//
				.put(NumberUtil.toZeroString(data.length, AbstractNeptuneMsg.VALUE_LENGTH).getBytes())//
				.put(data);

		return buffer.array();
	}

}
