package com.galaxia.gameculture.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.galaxia.MocMessageGenerator;
import com.galaxia.engdev.msg.NeptuneMsg;
import com.galaxia.gameculture.msg.impl.CertReqMsg;

class GamecultureMsgGeneratorTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void test() {
		CertReqMsg mocCertReqMsg = MocMessageGenerator.getMocCertReqMsg();
		try {
			byte[] bytes = mocCertReqMsg.getBytes(null);
			System.out.println(new String(bytes));
			NeptuneMsg msg = new GamecultureMsgGenerator().getNeptuneMsg(bytes);
			System.out.println(msg.toString());

			assertEquals(msg, mocCertReqMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
