package com.galaxia.engdev.message.tag;

import com.galaxia.engdev.dexeption.NeptuneException;

/**
 * 메시지 태그 인터페이스
 *
 * @author mjhan
 *
 */
public interface IMessageTag {

	/**
	 * Message tag의 길이
	 *
	 * @return
	 */
	public int getLength();

	/**
	 * Message 의 Type
	 *
	 * @return
	 */
	public MessageType getMessageType();

	/**
	 * Message 의 Code
	 *
	 * @return
	 */
	public String getCode();

	/**
	 * Message 의 Name
	 *
	 * @return
	 */
	public String getName();

	/**
	 * obj 형태를 Tag type에 맞는 byte[] 로 변환
	 *
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public byte[] getByte(Object obj) throws NeptuneException;

}
