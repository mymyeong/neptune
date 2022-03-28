package com.galaxia.engdev.msg;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Properties;

import com.galaxia.engdev.crypto.NeptuneCipher;
import com.galaxia.engdev.errorcode.NeptuneErrorCode;
import com.galaxia.engdev.errorcode.ErrorCode;
import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.tag.MessageTag;
import com.galaxia.engdev.msg.tag.MessageType;
import com.galaxia.engdev.msg.tag.NeptuneHeader;
import com.galaxia.engdev.util.NumberUtil;
import com.galaxia.engdev.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Neptune Message
 *
 * @author mjhan
 *
 */
@Slf4j
@Getter
@Setter
public abstract class NeptuneMsg {

	public static final int MESSAGE_TAG_LENGTH = 4;
	public static final int MESSAGE_COUNT_LENGTH = 4;
	public static final int VALUE_LENGTH = 4;

	protected int messageLength;
	protected String version = MsgVersion.MESSAGE_VERSION_V1;
	protected String serviceId;
	protected String serviceCode;
	protected String command;
	protected String orderId;
	protected String orderDate;
	protected int numberOfRecord;

	protected static LinkedHashSet<MessageTag> headerSet = new LinkedHashSet<>();
	protected static LinkedHashSet<MessageTag> encHeaderSet = new LinkedHashSet<>();
	static {
		headerSet.add(NeptuneHeader.VERSION);
		headerSet.add(NeptuneHeader.SERVICE_ID);
		headerSet.add(NeptuneHeader.SERVICE_CODE);

		encHeaderSet.add(NeptuneHeader.COMMAND);
		encHeaderSet.add(NeptuneHeader.ORDER_ID);
		encHeaderSet.add(NeptuneHeader.ORDER_DATE);
		encHeaderSet.add(NeptuneHeader.NUMBER_OF_RECORD);
	}

	@Override
	public String toString() {
		return headerLogStr();
	}

	public String headerLogStr() {
		StringBuilder builder = new StringBuilder();
		builder.append("[version=").append(version)//
				.append(", serviceId=").append(serviceId)//
				.append(", serviceCode=").append(serviceCode)//
				.append(", command=").append(command)//
				.append(", orderId=").append(orderId)//
				.append(", orderDate=").append(orderDate)//
				.append("]");
		return builder.toString();
	}

	/**
	 * 객체 필드변수 message tag set을 가져옴
	 *
	 * @return
	 */
	public LinkedHashSet<MessageTag> fieldMessageTagSet() {
		LinkedHashSet<MessageTag> set = new LinkedHashSet<>();
		for (Field f : this.getClass().getDeclaredFields()) {
			// 내부 객체 필드 제외
			if (f.getName().equals("this$0")) {
				continue;
			}

			try {
				MessageTag msgTag = getNeptuneMsgTagByTagName(f.getName());
				if (msgTag == null) {
					log.info("NeptuneMsgTag is null[" + f.getName() + "]");
				} else {
					set.add(msgTag);
				}
			} catch (Exception e) {
				log.info("해당 태그를 찾을 수 없습니다.[" + f.getName() + "]");
			}
		}

		return set;
	}

	public abstract MessageTag getNeptuneMsgTagByTagName(String tagName);

	public abstract MessageTag getNeptuneMsgTagByCode(String code);

	/**
	 * 헤더정보 복사
	 *
	 * @param msg
	 */
	public void copyHeader(NeptuneMsg msg) {
		this.version = msg.getVersion();
		this.serviceCode = msg.getServiceCode();
		this.serviceId = msg.getServiceId();
		this.orderDate = msg.getOrderDate();
		this.orderId = msg.getOrderId();
	}

	public Properties propertiesData() {
		Properties prop = new Properties();

		fieldMessageTagSet().stream().forEach(v -> {
			try {
				prop.put(v.getName(), this.getClass().getMethod(StringUtil.getGetterName(v.getName())).invoke(this));
			} catch (Exception e1) {
				// e1.printStackTrace();
			}
		});

		return prop;
	}

	public Properties propertiesData(Properties prop) {
		fieldMessageTagSet().stream().forEach(v -> {
			try {
				prop.put(v.getName(), this.getClass().getMethod(StringUtil.getGetterName(v.getName())).invoke(this));
			} catch (Exception e1) {
				// e1.printStackTrace();
			}
		});

		return prop;
	}

	public void setHeader(HashMap<NeptuneHeader, String> headerData) {
		this.messageLength = Integer.parseInt(Optional.ofNullable(headerData.get(NeptuneHeader.MESSAGE_LENGTH)).orElse("0"));
		this.command = headerData.get(NeptuneHeader.COMMAND);
		this.numberOfRecord = Integer.parseInt(Optional.ofNullable(headerData.get(NeptuneHeader.NUMBER_OF_RECORD)).orElse("0"));
		this.orderDate = headerData.get(NeptuneHeader.ORDER_DATE);
		this.orderId = headerData.get(NeptuneHeader.ORDER_ID);
		this.serviceCode = headerData.get(NeptuneHeader.SERVICE_CODE);
		this.serviceId = headerData.get(NeptuneHeader.SERVICE_ID);
		this.version = headerData.get(NeptuneHeader.VERSION);
	}

	public void setBody(byte[] bodyData) throws NeptuneException {
		int pos = 0;
		for (int i = 0; i < numberOfRecord; i++) {
			try {
				// MESSAGE TAG CODE
				String tagCode = new String(Arrays.copyOfRange(bodyData, pos, pos += MESSAGE_TAG_LENGTH));
				// MESSAGE CNT
				int messageCnt = Integer.parseInt(new String(Arrays.copyOfRange(bodyData, pos, pos += MESSAGE_COUNT_LENGTH)));
				// VALUE LENGTH(unused)
				Integer.parseInt(new String(Arrays.copyOfRange(bodyData, pos, pos += VALUE_LENGTH)));

				MessageTag tag = getNeptuneMsgTagByCode(tagCode);

				// 태그를 못찾을시
				if (tag == null) {
					int dataLength = Integer.parseInt(new String(Arrays.copyOfRange(bodyData, pos, pos += VALUE_LENGTH)));
					Arrays.copyOfRange(bodyData, pos, pos += dataLength);
					continue;
				}

				String setterName = StringUtil.getSetterName(tag.getName());
				MessageType messageType = tag.getMessageType();

				if (messageCnt == 1) {
					int dataLength = Integer.parseInt(new String(Arrays.copyOfRange(bodyData, pos, pos += VALUE_LENGTH)));
					String temp = new String(Arrays.copyOfRange(bodyData, pos, pos += dataLength)).trim();
					Object obj = messageType.getObject(temp);
					this.getClass().getMethod(setterName, messageType.getTagClass()).invoke(this, obj);

				} else if (messageCnt > 1) {
					int dataLength = 0;
					Object dataArray[];

					if (messageType.isInteger()) {
						dataArray = new Integer[messageCnt];
					} else {
						dataArray = new String[messageCnt];
					}

					for (int j = 0; j < messageCnt; j++) {
						dataLength = Integer.parseInt(new String(Arrays.copyOfRange(bodyData, pos, pos += VALUE_LENGTH)));
						String obj = new String(Arrays.copyOfRange(bodyData, pos, pos += dataLength)).trim();
						Object object = messageType.getObject(obj);

						dataArray[j] = object;
					}

					this.getClass().getMethod(setterName, messageType.getTagClass()).invoke(this, new Object[] { dataArray });

				}

			} catch (NoSuchMethodException e) {
				// 메시지 무시
			} catch (Exception e) {
				log.error("메시지 파싱 에러");
				log.error("error", e);
				throw new NeptuneException(NeptuneErrorCode.MESSAGE_TAG_TYPE_ERROR);
			}
		}
	}

	public byte[] getBytes(NeptuneCipher cipher) throws Exception {
		return getBytesFromMessageTagSet(fieldMessageTagSet(), cipher);
	}

	public byte[] getBytesFromMessageTagSet(LinkedHashSet<MessageTag> set, NeptuneCipher cipher) throws Exception {

		// 메시지 바디 생성
		byte[] body = getBodyByteArray(set);

		// 암호화 헤더
		byte[] encHeader = getHeaderByte(encHeaderSet);

		ByteBuffer bodyBuffer = ByteBuffer.allocate(body.length + encHeader.length);
		bodyBuffer.put(encHeader).put(body);

		// 암호화 메시지 처리
		if (cipher != null) {
			body = Base64.getEncoder().encode(cipher.encrypt(bodyBuffer.array()));
		} else {
			body = bodyBuffer.array();
		}

		// 암호화 하지 않는 헤더, 메시지 전체 길이 헤더생성을 위해 암호화 바디 생성 후 진행
		byte[] header = getHeaderByte(headerSet);

		ByteBuffer bb = ByteBuffer.allocate(NeptuneHeader.MESSAGE_LENGTH.getLength() + header.length + body.length);

		bb.put(NumberUtil.toZeroString(header.length + body.length, NeptuneHeader.MESSAGE_LENGTH.getLength()).getBytes())//
				.put(header)//
				.put(body);

		return bb.array();
	}

	protected byte[] getHeaderByte(LinkedHashSet<MessageTag> set) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(getMsgSetLength(set));
		for (MessageTag tag : set) {
			String getterName = StringUtil.getGetterName(tag.getName());
			Object obj = this.getClass().getMethod(getterName).invoke(this);

			if (tag.getMessageType() == MessageType.IntegerType) {
				int i = (int) obj;
				buffer.put(NumberUtil.toZeroString(i, tag.getLength()).getBytes());
			} else {
				obj = (obj == null) ? "" : obj;
				buffer.put(StringUtil.stringToFillSpaceByte(obj.toString(), tag.getLength()));
			}
		}

		return buffer.array();
	}

	protected int getMsgSetLength(LinkedHashSet<MessageTag> set) {
		return set.stream().mapToInt(v -> v.getLength()).sum();
	}

	protected byte[] getBodyByteArray(LinkedHashSet<MessageTag> set) throws Exception {
		ArrayList<byte[]> dataList = new ArrayList<>();
		int totalLength = 0, totalCnt = 0;
		for (MessageTag tag : set) {
			String getterName = StringUtil.getGetterName(tag.getName());

			Object obj = this.getClass().getMethod(getterName).invoke(this);

			if (obj != null) {
				byte[] data = tag.getByte(obj);
				dataList.add(data);
				totalLength += data.length;
				totalCnt++;
			}
		}

		ByteBuffer buffer = ByteBuffer.allocate(totalLength);

		for (byte[] data : dataList) {
			buffer.put(data);
		}

		setNumberOfRecord(totalCnt);

		return buffer.array();
	}

	public void setResponse(ErrorCode errorCode) {
		if (this instanceof ResponseMsg) {
			ResponseMsg msg = (ResponseMsg) this;
			msg.setResponseCode(errorCode.getResponseCode());
			msg.setResponseMsg(errorCode.getResponseMsg());
			msg.setDetailCode(errorCode.getDetailCode());
			msg.setDetailMsg(errorCode.getDetailMsg());
		}
	}
}
