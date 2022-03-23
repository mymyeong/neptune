package com.galaxia.gameculture.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.gameculture.msg.impl.CertReqMsg;
import com.galaxia.gameculture.msg.impl.CertRespMsg;
import com.galaxia.gameculture.proc.impl.CertProcess;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GamecultureController {

	@PostMapping(path = "/cert")
	public CertRespMsg cert(@Valid @RequestBody CertReqMsg reqMsg) throws NeptuneException {
		log.info(reqMsg.toString());
		CertRespMsg certRespMsg = (CertRespMsg) new CertProcess().proc(reqMsg);
		log.info(certRespMsg.toString());
		return certRespMsg;
	}
}
