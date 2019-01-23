package me.yushuo.wenda.async.handler;

import me.yushuo.wenda.async.EventHandler;
import me.yushuo.wenda.async.EventModel;
import me.yushuo.wenda.async.EventType;
import me.yushuo.wenda.model.Message;
import me.yushuo.wenda.model.User;
import me.yushuo.wenda.service.MessageService;
import me.yushuo.wenda.service.UserService;
import me.yushuo.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                    + "赞了你的评论，http://127.0.0.1:8080/quesion/" + model.getExt("questionId"));

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
