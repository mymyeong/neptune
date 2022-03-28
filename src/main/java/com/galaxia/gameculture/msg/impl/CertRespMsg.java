package com.galaxia.gameculture.msg.impl;

import com.galaxia.engdev.msg.ResponseMsg;
import com.galaxia.gameculture.msg.GameCultureMsg;
import com.galaxia.gameculture.msg.GamecultureCommand;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CertRespMsg extends GameCultureMsg implements ResponseMsg {

	public CertRespMsg() {
		setServiceCode("0300");
		setCommand(GamecultureCommand.CERT_RESP.getCommand());
	}

	private String responseCode;
	private String responseMsg;
	private String detailCode;
	private String detailMsg;

}
