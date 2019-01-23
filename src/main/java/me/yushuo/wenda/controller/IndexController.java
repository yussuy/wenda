package me.yushuo.wenda.controller;

import me.yushuo.wenda.model.EntityType;
import me.yushuo.wenda.model.Question;
import me.yushuo.wenda.model.ViewObject;
import me.yushuo.wenda.service.FollowService;
import me.yushuo.wenda.service.QuestionService;
import me.yushuo.wenda.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;


    @RequestMapping(path = {"/index", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(value = "pop",defaultValue = "0") int pop) {
        model.addAttribute("vos", getQuestions(pop, 0, 10));
        return "index";
    }

    @RequestMapping(value = "/user/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId){
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        return "index";
    }

    public List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        //freemarker用obj.field表示支持Obj有getfield方法或obj是Map类型，field是Map的key
//        List<Map<String, Object>> vos = new ArrayList<>();
//        for (Question question : questionList) {
//            Map vo = new HashMap();
//            vo.put("question", question);
//            vo.put("user", userService.getUser(question.getUserId()));
//            vos.add(vo);
//        }
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
