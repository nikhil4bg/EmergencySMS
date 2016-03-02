package com.praxisgs.emergencysms.eventbus;

import de.greenrobot.event.EventBus;

/**
 * Created on 04/02/2016.
 */
public class EmergencySMSEventBus {
    private static EventBus eventbus;

    public static void initialise(){
        eventbus = EventBus.builder().build();
    }

    public static void register(Object subscriber){
        if(!eventbus.isRegistered(subscriber))
        eventbus.register(subscriber);
    }

    public static void unregister(Object subscriber){
        if(eventbus.isRegistered(subscriber))
            eventbus.unregister(subscriber);
    }

    public static void post(Object event){
        eventbus.post(event);
    }

    public static void clear(){
        eventbus.clearCaches();
    }
}
