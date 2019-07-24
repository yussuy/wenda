package me.yushuo.wenda.controller;

import me.yushuo.wenda.model.EntityType;
import me.yushuo.wenda.model.HostHolder;
import me.yushuo.wenda.model.Question;
import me.yushuo.wenda.model.ViewObject;
import me.yushuo.wenda.service.FollowService;
import me.yushuo.wenda.service.QuestionService;
import me.yushuo.wenda.service.SearchService;
import me.yushuo.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    SearchService searchService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList = searchService.searchQuestion(keyword, offset, count,
                    "<b style=\"color:red\">", "</b>");
            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList) {
                Question q = questionService.getQuestionById(question.getId());
                ViewObject vo = new ViewObject();
                if (question.getContent()!=null) {
                    q.setContent(question.getContent());
                }
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                vo.set("question", q);
                vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
                vo.set("user", userService.getUser(q.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
        } catch (Exception e) {
            logger.error("搜索失败" + e.getMessage());
        }

        return "result";
    }
}
