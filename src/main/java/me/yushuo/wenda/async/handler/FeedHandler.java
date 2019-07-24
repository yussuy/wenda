package me.yushuo.wenda.async.handler;

import com.alibaba.fastjson.JSONObject;
import me.yushuo.wenda.async.EventHandler;
import me.yushuo.wenda.async.EventModel;
import me.yushuo.wenda.async.EventType;
import me.yushuo.wenda.model.EntityType;
import me.yushuo.wenda.model.Feed;
import me.yushuo.wenda.model.Question;
import me.yushuo.wenda.model.User;
import me.yushuo.wenda.service.FeedService;
import me.yushuo.wenda.service.QuestionService;
import me.yushuo.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class FeedHandler implements EventHandler {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    FeedService feedService;

    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<>();
        User actor = userService.getUser(model.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (model.getType() == EventType.COMMENT ||
                (model.getType() == EventType.FOLLOW &&
                        model.getEntityType() == EntityType.ENTITY_QUESTION)) {
            Question question = questionService.getQuestionById(model.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }

        return null;
    }

    @Override
    public void doHandle(EventModel model) {
        Feed feed = new Feed();
        feed.setCreatedId(new Date());
        feed.setUserId(model.getActorId());
        feed.setType(model.getType().getValue());
        feed.setData(buildFeedData(model));
        if (feed.getData() == null) {
            return;
        }

        feedService.addFeed(feed);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }
}
