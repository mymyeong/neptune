package com.galaxia.gameculture.proc;

import org.springframework.stereotype.Component;

import com.galaxia.engdev.proc.NeptuneProc;
import com.galaxia.engdev.proc.NeptuneProcessHadler;
import com.galaxia.gameculture.msg.GamecultureCommand;

@Component
public class GamecultureProcessHadler implements NeptuneProcessHadler {

	@Override
	public NeptuneProc getNeptuneProc(String command) {
		GamecultureCommand gamecultureCommand = GamecultureCommand.getCommand(command);
		if (gamecultureCommand != null) {
			return gamecultureCommand.getProc();
		} else {
			return null;
		}
	}

}
