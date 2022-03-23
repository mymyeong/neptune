package com.galaxia.gameculture.msg.impl;

import com.galaxia.gameculture.msg.AbtractGameCultureMsg;
import com.galaxia.gameculture.msg.GamecultureCommand;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CertRespMsg extends AbtractGameCultureMsg {

	public CertRespMsg() {
		setServiceCode("0300");
		setCommand(GamecultureCommand.CERT_RESP.getCommand());
	}

	private String respCode;
	private String respMsg;
	private String detailCode;
	private String detailMsg;

}
