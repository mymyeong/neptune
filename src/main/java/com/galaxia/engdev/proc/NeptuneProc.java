package com.galaxia.engdev.proc;

import java.util.HashMap;

import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.NeptuneMsg;

public abstract class NeptuneProc {

	private HashMap<String, Object> dataMap = new HashMap<>();

	public NeptuneMsg proc(NeptuneMsg msg) throws NeptuneException {

		request(msg);

		affComm(dataMap);

		return response();
	}

	public abstract void request(NeptuneMsg reqMsg) throws NeptuneException;

	public abstract NeptuneMsg response();

	public abstract void affComm(HashMap<String, Object> dataMap);
}
