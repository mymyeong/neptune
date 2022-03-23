package com.galaxia;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.galaxia.gameculture.msg.impl.CertReqMsg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "netty.tcp-port: 9999" })
class EngdevSpringBootApplicationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		log.info("setUpBeforeClass");
	}

	@AfterAll
	static void afterAllClass() throws Exception {
		log.info("afterAllClass");
	}

	@Test
	void certReqSendTest() throws Exception {

		log.info("certReqSendTest");

		CertReqMsg certReqMsg = MocMessageGenerator.getMocCertReqMsg();
		Socket socket = new Socket();
		InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 30300);
		OutputStream os = null;
		InputStream is = null;

		try {
			socket.setSoTimeout(3000);
			socket.connect(isa);

			os = socket.getOutputStream();
			is = socket.getInputStream();
			byte[] temp = certReqMsg.getBytes(null);
			log.info("REQ DATA : " + new String(temp));
			os.write(temp);
			os.flush();

			byte[] temp2 = new byte[4];
			is.read(temp2);

			int messageLen = Integer.parseInt(new String(temp));

			temp2 = new byte[messageLen];
			is.read(temp2);

			log.info("RESP : " + new String(temp2));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
			}
		}

		assertTrue(true);
	}

}
