package com.galaxia.gameculture.proc;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.galaxia.MocMessageGenerator;
import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.AbstractNeptuneMsg;
import com.galaxia.engdev.proc.NeptuneProc;
import com.galaxia.gameculture.msg.GamecultureCommand;

class GamecultureProcessHadlerTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void test() {
		try {
			// given
			NeptuneProc neptuneProc = new GamecultureProcessHadler().getNeptuneProc(GamecultureCommand.CERT_REQ.getCommand());

			// when
			AbstractNeptuneMsg respMsg = neptuneProc.proc(MocMessageGenerator.getMocCertReqMsg());

			// then
			assertNotNull(respMsg);
		} catch (NeptuneException e) {
			e.printStackTrace();
		}
	}
}
