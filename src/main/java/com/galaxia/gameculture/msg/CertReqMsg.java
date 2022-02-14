package com.galaxia.gameculture.msg;

import com.galaxia.engdev.message.AbstractNeptuneMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertReqMsg extends AbstractNeptuneMsg {

	private String id;

	private Integer amount;
}
