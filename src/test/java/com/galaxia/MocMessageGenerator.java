package com.galaxia;

import com.galaxia.gameculture.msg.impl.CertReqMsg;

public class MocMessageGenerator {

	public static CertReqMsg getMocCertReqMsg() {
		CertReqMsg certReqMsg = new CertReqMsg();
		certReqMsg.setServiceId("glx_api");
		certReqMsg.setOrderDate("20220323105900");
		certReqMsg.setOrderId("TEST_ORDER_ID_0001");
		certReqMsg.setAmount(1000);
		certReqMsg.setItemName("TEST_ITEM_NAME_아이템이름명테스트");
		String[] pins = { "000A1", "BBB21" };
		certReqMsg.setPinNumber(pins);
		return certReqMsg;
	}
}
