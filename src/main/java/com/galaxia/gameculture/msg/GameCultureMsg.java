package com.galaxia.gameculture.msg;

import com.galaxia.engdev.msg.NeptuneMsg;
import com.galaxia.engdev.msg.tag.MessageTag;
import com.galaxia.gameculture.msg.tag.GamecultureMsgTag;

public abstract class GameCultureMsg extends NeptuneMsg {

	@Override
	public MessageTag getNeptuneMsgTagByTagName(String tagName) {
		return GamecultureMsgTag.getByTagName(tagName);
	}

	@Override
	public MessageTag getNeptuneMsgTagByCode(String code) {
		return GamecultureMsgTag.getByTagCode(code);
	}
}
