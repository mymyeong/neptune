package com.galaxia.engdev.proc;

import java.util.HashMap;

import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.AbstractNeptuneMsg;

public abstract class AbstractNeptuneProc implements NeptuneProc {

	private HashMap<String, Object> dataMap = new HashMap<>();

	@Override
	public AbstractNeptuneMsg proc(AbstractNeptuneMsg msg) throws NeptuneException {

		request(msg);

		affComm(dataMap);

		return response();
	}

	public abstract void request(AbstractNeptuneMsg reqMsg) throws NeptuneException;

	public abstract AbstractNeptuneMsg response();

	public abstract void affComm(HashMap<String, Object> dataMap);
}
