package me.yushuo.wenda.controller;

import me.yushuo.wenda.model.*;
import me.yushuo.wenda.service.CommentService;
import me.yushuo.wenda.service.LikeService;
import me.yushuo.wenda.service.QuestionService;
import me.yushuo.wenda.service.UserService;
import me.yushuo.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

    @RequestMapping("/question/add")
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            //question.setCommentCount(0);
            if (hostHolder.getUser() != null) {
                question.setUserId(hostHolder.getUser().getId());
            } else {
                question.setUserId(WendaUtil.ANONYMOUS_USER);
            }
            if(questionService.addQuestion(question) > 0) {
                return WendaUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加问题失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "服务器出错");
    }

    @RequestMapping("/question/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getQuestionById(qid);
        model.addAttribute("question", question);

        List<ViewObject> vos = new ArrayList<>();

        List<Comment> comments = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        for (Comment comment : comments) {
            ViewObject vo = new ViewObject();
            User user = userService.getUser(comment.getUserId());
            vo.set("comment", comment);
            if (hostHolder.getUser()==null) {
                vo.set("liked", 0);
            } else {
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT, qid));
            }
            vo.set("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT, qid));
            vo.set("user", user);
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "detail";
    }
}
