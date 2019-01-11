package me.yushuo.wenda.controller;

import me.yushuo.wenda.model.HostHolder;
import me.yushuo.wenda.model.Message;
import me.yushuo.wenda.model.User;
import me.yushuo.wenda.service.MessageService;
import me.yushuo.wenda.service.SensitiveService;
import me.yushuo.wenda.service.UserService;
import me.yushuo.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Date;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SensitiveService sensitiveService;

    @RequestMapping(value = "/msg/addMessage", method = RequestMethod.POST)
    public String addMeassage(@RequestParam("toName") String toName,
                              @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "未登录");
            }
            User user = userService.getUserByName(toName);
            if (user == null) {
                return WendaUtil.getJSONString(1, "用户不存在");
            }

            Message message = new Message();
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            message.setCreatedDate(new Date());
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("增加站内信失败" + e.getMessage());
            return WendaUtil.getJSONString(1, "插入站内信失败");
        }
    }

//    @RequestMapping(value = "/msg/detail", method = RequestMethod.GET)
//    public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
//
//    }

}
