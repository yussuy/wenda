package me.yushuo.wenda.controller;

import me.yushuo.wenda.model.Comment;
import me.yushuo.wenda.model.EntityType;
import me.yushuo.wenda.model.HostHolder;
import me.yushuo.wenda.service.CommentService;
import me.yushuo.wenda.service.QuestionService;
import me.yushuo.wenda.service.SensitiveService;
import me.yushuo.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(Model model, @RequestParam("questionId") int questionId,
                          @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);

            comment.setContent(content);
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            if (hostHolder.getUser() == null) {
                comment.setUserId(WendaUtil.ANONYMOUS_USER);
            } else {
                comment.setUserId(hostHolder.getUser().getId());
            }
            commentService.addComment(comment);
            // 更新题目里的评论数量
            int count = commentService.getCommentCount(questionId, EntityType.ENTITY_QUESTION);
            questionService.updateCommentCount(count, questionId);
            // 怎么异步化
        } catch (Exception e) {
            logger.error("添加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}
