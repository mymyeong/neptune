package com.galaxia.engdev.message.tag;

import java.util.ArrayList;
import java.util.List;

public class NeptuneMsgTagList {

	private static List<NeptuneMsgTag> NEPTUNE_MESSAGE_TAG_LIST = new ArrayList<>();

	public static List<NeptuneMsgTag> getMsgTagList() {
		return NEPTUNE_MESSAGE_TAG_LIST;
	}

	public static void addTag(NeptuneMsgTag msgTag) {
		NEPTUNE_MESSAGE_TAG_LIST.add(msgTag);
	}

	public static NeptuneMsgTag getNeptuneTag(String messageCode) {
		return NEPTUNE_MESSAGE_TAG_LIST.stream().filter(v -> v.getCode().equals(messageCode)).findFirst().orElse(null);
	}

	public static NeptuneMsgTag getNeptuneTagByName(String msgName) {
		return NEPTUNE_MESSAGE_TAG_LIST.stream().filter(v -> v.getName().equals(msgName)).findFirst().orElse(null);
	}

	public static int getSize() {
		return NEPTUNE_MESSAGE_TAG_LIST.size();
	}
}
