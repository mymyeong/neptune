package com.galaxia.gameculture.proc.impl;

import java.util.HashMap;

import com.galaxia.engdev.errorcode.NeptuneErrorCode;
import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.NeptuneMsg;
import com.galaxia.engdev.proc.NeptuneProc;
import com.galaxia.gameculture.errorcode.GamecultureErrorcode;
import com.galaxia.gameculture.msg.impl.CertReqMsg;
import com.galaxia.gameculture.msg.impl.CertRespMsg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CertProcess extends NeptuneProc {

	protected CertReqMsg certReqMsg;
	protected CertRespMsg certRespMsg;

	@Override
	public void request(NeptuneMsg reqMsg) throws NeptuneException {
		if (reqMsg instanceof CertReqMsg) {
			certReqMsg = (CertReqMsg) reqMsg;
		} else {
			throw new NeptuneException(GamecultureErrorcode.REQ_MESSAGE_TYPE_ERROR);
		}

		log.info(certReqMsg.toString());
	}

	@Override
	public NeptuneMsg response() {
		certRespMsg = new CertRespMsg();
		certRespMsg.copyHeader(certReqMsg);

		certRespMsg.setResponse(NeptuneErrorCode.SUCCESS);

		log.info(certRespMsg.toString());
		return certRespMsg;
	}

	@Override
	public void affComm(HashMap<String, Object> dataMap) {
		// TODO : 제휴사 호출 로직
	}

}
