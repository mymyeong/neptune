package com.galaxia.engdev.proc;

import com.galaxia.engdev.exception.NeptuneException;
import com.galaxia.engdev.msg.AbstractNeptuneMsg;

public interface NeptuneProc {

	public AbstractNeptuneMsg proc(AbstractNeptuneMsg msg) throws NeptuneException;
}
