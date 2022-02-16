package com.galaxia.gameculture.msg;

import com.galaxia.engdev.message.AbstractNeptuneMsg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class CertReqMsg extends AbstractNeptuneMsg {

	private String id;

	private Integer amount;
}
