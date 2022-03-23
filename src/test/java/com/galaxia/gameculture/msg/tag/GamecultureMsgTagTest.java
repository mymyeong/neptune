package com.galaxia.gameculture.msg.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GamecultureMsgTagTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void test() {
		// given
		GamecultureMsgTag tag = GamecultureMsgTag.getByTagName("amount");

		// then
		assertEquals(tag, GamecultureMsgTag.AMOUNT);
	}

}
