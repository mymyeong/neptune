package com.galaxia.engdev.msg;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import com.galaxia.engdev.crypto.NeptuneCipher;
import com.galaxia.engdev.errorcode.NeptuneErrorCode;
import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.tag.NeptuneHeader;

public interface NeptuneMsgGeneratorble {

	public AbstractNeptuneMsg getNeptuneMsg(byte[] data);

	public static HashMap<NeptuneHeader, String> getHeaderData(byte[] data) throws NeptuneException {

		HashMap<NeptuneHeader, String> headerData = new HashMap<>();

		int pos = 0;
//		String messageLength = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.MESSAGE_LENGTH.getLength())).trim();
		String version = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.VERSION.getLength())).trim();
		String serviceId = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.SERVICE_ID.getLength())).trim();
		String serviceCode = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.SERVICE_CODE.getLength())).trim();

//		headerData.put(NeptuneHeader.MESSAGE_LENGTH, messageLength);
		headerData.put(NeptuneHeader.VERSION, version);
		headerData.put(NeptuneHeader.SERVICE_ID, serviceId);
		headerData.put(NeptuneHeader.SERVICE_CODE, serviceCode);

		try {
			NeptuneCipher cipher = getMsgCipher(version, serviceId, serviceCode);
			if (cipher != null) {
				data = Arrays.copyOfRange(data, pos, data.length);
				data = cipher.decrypt(Base64.getDecoder().decode(data));
				pos = 0;
			}
		} catch (Exception e) {
			throw new NeptuneException(NeptuneErrorCode.MESSAGE_DECRYPT_ERROR);
		}

		String command = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.COMMAND.getLength())).trim();
		String orderId = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.ORDER_ID.getLength())).trim();
		String orderDate = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.ORDER_DATE.getLength())).trim();
		String numberOfRecord = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.NUMBER_OF_RECORD.getLength())).trim();

		headerData.put(NeptuneHeader.COMMAND, command);
		headerData.put(NeptuneHeader.ORDER_ID, orderId);
		headerData.put(NeptuneHeader.ORDER_DATE, orderDate);
		headerData.put(NeptuneHeader.NUMBER_OF_RECORD, numberOfRecord);

		return headerData;
	}

	public static NeptuneCipher getMsgCipher(String version, String serviceId, String serviceCode) {
		// TODO 암호화 객체 생성 로직 추가
		return null;
	}

}
