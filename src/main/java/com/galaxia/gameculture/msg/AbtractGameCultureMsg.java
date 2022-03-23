package com.galaxia.gameculture.msg;

import com.galaxia.engdev.msg.AbstractNeptuneMsg;
import com.galaxia.engdev.msg.tag.MessageTag;
import com.galaxia.gameculture.msg.tag.GamecultureMsgTag;

public abstract class AbtractGameCultureMsg extends AbstractNeptuneMsg {

	@Override
	public MessageTag getNeptuneMsgTagByTagName(String tagName) {
		return GamecultureMsgTag.getByTagName(tagName);
	}

	@Override
	public MessageTag getNeptuneMsgTagByCode(String code) {
		return GamecultureMsgTag.getByTagCode(code);
	}
}
