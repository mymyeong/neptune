package com.galaxia.gameculture.msg;

import java.util.Arrays;

import com.galaxia.engdev.msg.NeptuneCommand;
import com.galaxia.engdev.msg.NeptuneMsg;
import com.galaxia.engdev.proc.NeptuneProc;
import com.galaxia.gameculture.msg.impl.CertReqMsg;
import com.galaxia.gameculture.msg.impl.CertRespMsg;
import com.galaxia.gameculture.proc.impl.CertProcess;

import lombok.Getter;

public enum GamecultureCommand implements NeptuneCommand {

	/** 인증요청 */
	CERT_REQ("2000") {
		@Override
		public NeptuneMsg getMsg() {
			return new CertReqMsg();
		}

		@Override
		public NeptuneProc getProc() {
			return new CertProcess();
		}
	},

	/** 인증응답 */
	CERT_RESP("2001") {
		@Override
		public NeptuneMsg getMsg() {
			return new CertRespMsg();
		}

		@Override
		public NeptuneProc getProc() {
			return null;
		}
	},

	;

	@Getter
	String command;

	private GamecultureCommand(String command) {
		this.command = command;
	}

	public abstract NeptuneMsg getMsg();

	public abstract NeptuneProc getProc();

	public static GamecultureCommand getCommand(String command) {
		return Arrays.stream(values()) //
				.filter(v -> v.getCommand().equals(command)) //
				.findFirst() //
				.orElse(null) //
		;
	}
}
