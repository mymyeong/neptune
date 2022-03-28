package com.galaxia.gameculture.msg.impl;

import com.galaxia.gameculture.msg.GameCultureMsg;
import com.galaxia.gameculture.msg.GamecultureCommand;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CertReqMsg extends GameCultureMsg {

	public CertReqMsg() {
		setServiceCode("0300");
		setCommand(GamecultureCommand.CERT_REQ.getCommand());
	}

	private String pinNumber[];

	private Integer amount;

	private String itemName;

}
