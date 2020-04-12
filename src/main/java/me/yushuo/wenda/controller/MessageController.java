package me.yushuo.wenda.controller;

import me.yushuo.wenda.model.HostHolder;
import me.yushuo.wenda.model.Message;
import me.yushuo.wenda.model.User;
import me.yushuo.wenda.model.ViewObject;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "/msg/addMessage", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
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
            logger.error("发送消息失败" + e.getMessage());
            return WendaUtil.getJSONString(1, "发信失败");
        }
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {
        if (hostHolder.getUser() == null){
            return "redirect:/reglogin";
        }
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
        List<ViewObject> conversations = new ArrayList<>();
        for (Message message : conversationList) {
            ViewObject vo = new ViewObject();
            int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            vo.set("user", userService.getUser(targetId));
            vo.set("message", message);
            vo.set("unread", messageService.getUnreadCount(localUserId, message.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            messageService.hasRead(hostHolder.getUser().getId());
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", userService.getUser(message.getFromId()));
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取详情失败" + e.getMessage());
        }
        return "letterDetail";
    }

//    @RequestMapping(value = "/msg/detail", method = RequestMethod.GET)
//    public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
//
//    }

}
