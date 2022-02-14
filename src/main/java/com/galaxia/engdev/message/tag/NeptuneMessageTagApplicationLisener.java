package com.galaxia.engdev.message.tag;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Message tag init
 * 
 * @author mjhan
 *
 */
@Slf4j
@Component
public class NeptuneMessageTagApplicationLisener implements ApplicationListener<ApplicationStartedEvent> {

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		MessageTagDao.NEPTUNE_MESSAGE_TAG_LIST.add(new NeptuneMsgTag(4, "", "", MessageType.String));

		log.info("MESSAGE TAG 초기화 완료 : CNT : {}", MessageTagDao.NEPTUNE_MESSAGE_TAG_LIST.size());
	}

}
