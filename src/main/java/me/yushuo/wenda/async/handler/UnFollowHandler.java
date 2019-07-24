package me.yushuo.wenda.async.handler;

import me.yushuo.wenda.async.EventHandler;
import me.yushuo.wenda.async.EventModel;
import me.yushuo.wenda.async.EventType;

import java.util.Arrays;
import java.util.List;

public class UnFollowHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.UNFOLLOW);
    }
}
