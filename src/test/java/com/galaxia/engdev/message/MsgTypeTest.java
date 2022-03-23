package com.galaxia.engdev.message;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.galaxia.engdev.msg.tag.MessageType;

class MsgTypeTest {

	@Test
	void test() {
		int[] intTemp = { 1, 2 };
		Integer[] intgerArrTemp = { 1, 2 };
		String[] StrArrTemp = { "A", "B" };
		Integer intgerTemp = 1;

		assertTrue(MessageType.Integer.typeChecker(1));
		assertTrue(MessageType.Integer.typeChecker(intgerTemp));
		assertTrue(MessageType.IntArray.typeChecker(intTemp));
		assertTrue(MessageType.IntegerArray.typeChecker(intgerArrTemp));
		assertTrue(MessageType.String.typeChecker(new String("")));
		assertTrue(MessageType.StringArray.typeChecker(StrArrTemp));
		assertTrue(MessageType.Yn.typeChecker("Y"));
		assertTrue(MessageType.Yn.typeChecker("N"));
		assertTrue(MessageType.IpAddressV4.typeChecker("127.0.0.1"));

		assertFalse(MessageType.Integer.typeChecker(new String("")));
		assertFalse(MessageType.IntArray.typeChecker(intgerArrTemp));
		assertFalse(MessageType.IntegerArray.typeChecker(StrArrTemp));
		assertFalse(MessageType.String.typeChecker(intgerTemp));
		assertFalse(MessageType.StringArray.typeChecker(intgerArrTemp));
		assertFalse(MessageType.Yn.typeChecker("C"));
		assertFalse(MessageType.IpAddressV4.typeChecker("256.0.0.1"));
	}

}
