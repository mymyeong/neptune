package com.galaxia.gameculture.msg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.galaxia.MocMessageGenerator;
import com.galaxia.gameculture.msg.impl.CertReqMsg;

class AbtractGameCultureMsgTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	@DisplayName("getByte Test")
	void getByteTest() {

		CertReqMsg certReqMsg = MocMessageGenerator.getMocCertReqMsg();
		try {
			System.out.println(new String(certReqMsg.getBytes(null)));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Test
	@DisplayName("setByte Test")
	void setByteTest() {

	}

}
