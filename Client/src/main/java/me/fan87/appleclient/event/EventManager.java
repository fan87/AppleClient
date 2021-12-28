package me.fan87.appleclient.event;

import org.greenrobot.eventbus.EventBus;

public class EventManager {

    public final static EventBus EVENT_BUS = EventBus.builder()
            .logNoSubscriberMessages(false)
            .throwSubscriberException(false)
            .build();

}
