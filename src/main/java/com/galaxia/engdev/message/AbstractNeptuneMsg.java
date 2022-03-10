package com.galaxia.engdev.message;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.Properties;

import com.galaxia.engdev.crypto.AbstractCipher;
import com.galaxia.engdev.crypto.Seed;
import com.galaxia.engdev.dexeption.NeptuneException;
import com.galaxia.engdev.errorcode.NeptuneErrorCode;
import com.galaxia.engdev.message.tag.IMessageTag;
import com.galaxia.engdev.message.tag.MessageType;
import com.galaxia.engdev.message.tag.NeptuneHeader;
import com.galaxia.engdev.message.tag.NeptuneMsgTagList;
import com.galaxia.engdev.util.NumberUtil;
import com.galaxia.engdev.util.StringUtil;
import com.google.common.base.MoreObjects;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Neptune Message<br>
 *
 * @author mjhan
 *
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractNeptuneMsg {

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

	protected static LinkedHashSet<IMessageTag> headerSet = new LinkedHashSet<>();
	protected static LinkedHashSet<IMessageTag> encHeaderSet = new LinkedHashSet<>();
	static {
		headerSet.add(NeptuneHeader.VERSION);
		headerSet.add(NeptuneHeader.SERVICE_ID);
		headerSet.add(NeptuneHeader.SERVICE_CODE);

		encHeaderSet.add(NeptuneHeader.COMMAND);
		encHeaderSet.add(NeptuneHeader.ORDER_ID);
		encHeaderSet.add(NeptuneHeader.ORDER_DATE);
		encHeaderSet.add(NeptuneHeader.NUMBER_OF_RECORD);
	}

//	@Getter(AccessLevel.NONE)
//	protected ArrayList<IMessageTag> setDataTagList = new ArrayList<IMessageTag>();

	@Override
	public String toString() {
		return getHeaderLogStr();
	}

	public String getHeaderLogStr() {
		return MoreObjects.toStringHelper(this) //
				.add(version, "version")//
				.add(serviceId, "serviceId")//
				.add(serviceCode, "serviceCode")//
				.add(command, "command")//
				.add(orderId, "orderId")//
				.add(orderDate, "orderDate")//
				.toString();
	}

	public String getLogMessage() {
		return toString();
	}

	public byte[] getBytes() throws Exception {
		return getBytesFromMessageTagSet(getFieldMessageTagSet());
	}

	protected byte[] getBytesFromMessageTagSet(LinkedHashSet<IMessageTag> set) throws Exception {

		// 메시지 바디 생성
		byte[] body = getByteArray(set);

		// 암호화 헤더
		byte[] encHeader = getHeaderByte(encHeaderSet);

		ByteBuffer bodyBuffer = ByteBuffer.allocate(body.length + encHeader.length);
		bodyBuffer.put(encHeader).put(body);

		// 암호화 메시지 처리
		AbstractCipher cipher = getMsgCipher();
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

	protected AbstractCipher getMsgCipher() {
		AbstractCipher seed = new Seed();
		seed.setKey("".getBytes());
		seed.setIv("".getBytes());

		return seed;
	}

	protected byte[] getHeaderByte(LinkedHashSet<IMessageTag> set) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(getMsgSetLength(set));
		for (IMessageTag tag : set) {
			String getterName = StringUtil.getGetterName(tag.getName());
			Object obj = this.getClass().getMethod(getterName).invoke(this);

			if (tag.getMessageType() == MessageType.Integer) {
				int i = (int) obj;
				buffer.put(NumberUtil.toZeroString(i, tag.getLength()).getBytes());
			} else {
				obj = (obj == null) ? "" : obj;
				buffer.put(StringUtil.stringToFillSpaceByte(obj.toString(), tag.getLength()));
			}
		}

		return buffer.array();
	}

	protected int getMsgSetLength(LinkedHashSet<IMessageTag> set) {
		return set.stream().mapToInt(v -> v.getLength()).sum();
	}

	/**
	 * 객체 필드변수 message tag set을 가져옴
	 *
	 * @return
	 */
	protected LinkedHashSet<IMessageTag> getFieldMessageTagSet() {
		LinkedHashSet<IMessageTag> set = new LinkedHashSet<>();
		for (Field f : this.getClass().getDeclaredFields()) {
			// 내부 객체 필드 제외
			if (f.getName().equals("this$0")) {
				continue;
			}

			try {
				IMessageTag msgTag = NeptuneMsgTagList.getNeptuneTagByName(StringUtil.toUpperSnake(f.getName()));
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

	/**
	 * 헤더정보 복사
	 *
	 * @param msg
	 */
	public void copyHeader(AbstractNeptuneMsg msg) {
		this.version = msg.getVersion();
		this.serviceCode = msg.getServiceCode();
		this.serviceId = msg.getServiceId();
		this.orderDate = msg.getOrderDate();
		this.orderId = msg.getOrderId();
	}

	protected byte[] getByteArray(LinkedHashSet<IMessageTag> set) throws Exception {
		ArrayList<byte[]> dataList = new ArrayList<>();
		int totalLength = 0, totalCnt = 0;
		for (IMessageTag tag : set) {
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

	public void setData(byte[] data) throws NeptuneException {

		int pos = setHeader(data);

		setBody(Arrays.copyOfRange(data, pos, data.length));
	}

	protected int setHeader(byte[] data) throws NeptuneException {
		int pos = 0;

		String version = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.VERSION.getLength())).trim();
		String serviceId = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.SERVICE_ID.getLength())).trim();
		String serviceCode = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.SERVICE_CODE.getLength())).trim();

		setVersion(version);
		setServiceId(serviceId);
		setServiceCode(serviceCode);

		AbstractCipher cipher = null;
		try {
			cipher = getMsgCipher();
			if (cipher != null) {
				data = Arrays.copyOfRange(data, pos, data.length);
				data = cipher.decrypt(Base64.getDecoder().decode(data));
			}
		} catch (Exception e) {
			log.error("메시지 복호화 실패");
			log.error("error", e);
			throw new NeptuneException(NeptuneErrorCode.MESSAGE_DECRYPT_ERROR);
		}

		pos = 0;
		String command = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.COMMAND.getLength())).trim();
		String orderId = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.ORDER_ID.getLength())).trim();
		String orderDate = new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.ORDER_DATE.getLength())).trim();
		int numberOfRecord = Integer.parseInt(new String(Arrays.copyOfRange(data, pos, pos += NeptuneHeader.NUMBER_OF_RECORD.getLength())).trim());

		setCommand(command);
		setOrderId(orderId);
		setOrderDate(orderDate);
		setNumberOfRecord(numberOfRecord);

		return pos;
	}

	protected void setBody(byte[] data) throws NeptuneException {
		int pos = 0;
		for (int i = 0; i < numberOfRecord; i++) {
			try {
				String messageCode = new String(Arrays.copyOfRange(data, pos, pos += MESSAGE_TAG_LENGTH));
				int messageCnt = Integer.parseInt(new String(Arrays.copyOfRange(data, pos, pos += MESSAGE_COUNT_LENGTH)));
				Integer.parseInt(new String(Arrays.copyOfRange(data, pos, pos += VALUE_LENGTH)));

				IMessageTag tag = NeptuneMsgTagList.getNeptuneTag(messageCode);

				if (tag == null) {
					int dataLength = Integer.parseInt(new String(Arrays.copyOfRange(data, pos, pos += VALUE_LENGTH)));
					Arrays.copyOfRange(data, pos, pos += dataLength);
					continue;
				}

				String setterName = StringUtil.getSetterName(tag.getName());

				if (messageCnt == 1) {
					int dataLength = Integer.parseInt(new String(Arrays.copyOfRange(data, pos, pos += VALUE_LENGTH)));
					String temp = new String(Arrays.copyOfRange(data, pos, pos += dataLength)).trim();

					switch (tag.getMessageType()) {
					case Integer:
						if (NumberUtil.isNumber(temp)) {
							int n = Integer.parseInt(temp.equals("") ? "0" : temp);
//							if (n != 0) {
//								setDataTagList.add(tag);
//							}
							this.getClass().getMethod(setterName, Integer.class).invoke(this, n);
						} else {
							log.info("Integer 변환 에러[" + temp + "]");
							throw new NeptuneException(NeptuneErrorCode.MESSAGE_TAG_TYPE_ERROR);
						}
						break;

					case Yn:
						if ((!temp.equalsIgnoreCase("Y") || !temp.equalsIgnoreCase("N"))) {
							throw new NeptuneException(NeptuneErrorCode.MESSAGE_TAG_TYPE_ERROR);
						}
					case IpAddressV4:
						if (StringUtil.isIpAddressV4(temp)) {
							throw new NeptuneException(NeptuneErrorCode.MESSAGE_TAG_TYPE_ERROR);
						}
					case String:
					default:
						this.getClass().getMethod(setterName, String.class).invoke(this, temp);

//						if (!temp.equals("")) {
//							setDataTagList.add(tag);
//						}
						break;
					}
				} else if (messageCnt > 1) {
					int dataLength = 0;

					switch (tag.getMessageType()) {
					case IntegerArray:
					case IntArray:
						int intData[] = new int[messageCnt];

						for (int j = 0; j < messageCnt; j++) {
							dataLength = Integer.parseInt(new String(Arrays.copyOfRange(data, pos, pos += VALUE_LENGTH)));
							intData[j] = Integer.parseInt(new String(Arrays.copyOfRange(data, pos, pos += dataLength)));
						}

						this.getClass().getMethod(setterName, int[].class).invoke(this, intData);

						break;
					case StringArray:
					default:
						String strData[] = new String[messageCnt];
						for (int j = 0; j < messageCnt; j++) {
							dataLength = Integer.parseInt(new String(Arrays.copyOfRange(data, pos, pos += VALUE_LENGTH)));
							strData[j] = new String(Arrays.copyOfRange(data, pos, pos += dataLength)).trim();
						}

						this.getClass().getMethod(setterName, String[].class).invoke(this, new Object[] { strData });
						break;
					}
//					setDataTagList.add(tag);
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

	public Properties getPropertiesData() {
		Properties prop = new Properties();

		getFieldMessageTagSet().stream().forEach(v -> {
			try {
				prop.put(v.getName(), this.getClass().getMethod(StringUtil.getGetterName(v.getName())).invoke(this));
			} catch (Exception e1) {
				// e1.printStackTrace();
			}
		});

		return prop;
	}

	public Properties getPropertiesData(Properties prop) {
		getFieldMessageTagSet().stream().forEach(v -> {
			try {
				prop.put(v.getName(), this.getClass().getMethod(StringUtil.getGetterName(v.getName())).invoke(this));
			} catch (Exception e1) {
				// e1.printStackTrace();
			}
		});

		return prop;
	}
}
