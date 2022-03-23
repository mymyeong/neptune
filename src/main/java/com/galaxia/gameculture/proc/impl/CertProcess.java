package com.galaxia.gameculture.proc.impl;

import java.util.HashMap;

import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.AbstractNeptuneMsg;
import com.galaxia.engdev.proc.AbstractNeptuneProc;
import com.galaxia.gameculture.errorcode.GamecultureErrorcode;
import com.galaxia.gameculture.msg.impl.CertReqMsg;
import com.galaxia.gameculture.msg.impl.CertRespMsg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CertProcess extends AbstractNeptuneProc {

	protected CertReqMsg certReqMsg;
	protected CertRespMsg certRespMsg;

	@Override
	public void request(AbstractNeptuneMsg reqMsg) throws NeptuneException {
		if (reqMsg instanceof CertReqMsg) {
			certReqMsg = (CertReqMsg) reqMsg;
		} else {
			throw new NeptuneException(GamecultureErrorcode.REQ_MESSAGE_TYPE_ERROR);
		}

		log.info(certReqMsg.toString());
	}

	@Override
	public AbstractNeptuneMsg response() {
		certRespMsg = new CertRespMsg();
		certRespMsg.copyHeader(certReqMsg);
		certRespMsg.setRespCode("0000");
		certRespMsg.setRespMsg("success");
		certRespMsg.setDetailCode("00");
		certRespMsg.setDetailMsg("OK");
		log.info(certRespMsg.toString());
		return certRespMsg;
	}

	@Override
	public void affComm(HashMap<String, Object> dataMap) {
		// TODO : 제휴사 호출 로직
	}

}
