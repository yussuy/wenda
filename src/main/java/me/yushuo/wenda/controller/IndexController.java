package me.yushuo.wenda.controller;

import com.alibaba.fastjson.JSON;
import me.yushuo.wenda.model.*;
import me.yushuo.wenda.service.CommentService;
import me.yushuo.wenda.service.FollowService;
import me.yushuo.wenda.service.QuestionService;
import me.yushuo.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/index", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(value = "pop", defaultValue = "0") int pop,
                        @RequestParam(value = "offset", defaultValue = "0") int offset,
                        @RequestParam(value = "limit", defaultValue = "9") int limit) {
        //model.addAttribute("vos", getQuestions(pop, offset, limit));
        model.addAttribute("userId", pop);
        return "index";

    }

    /*
    // 另一种写法
    @RequestMapping(path = {"/index", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(Model model, @RequestParam(value = "pop",defaultValue = "0") int pop) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("vos", getQuestions(pop, 0, 9));
        mav.setViewName("index");
        return mav;
    }
     */

    @RequestMapping(value = "/user/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
//        model.addAttribute("vos", getQuestions(userId, 0, 10));
        model.addAttribute("userId", userId);
        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCunt", commentService.getUserCommentCount(userId));
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
        } else {
            vo.set("followed", false);
        }

        model.addAttribute("profileUser", vo);

        return "profile";
    }
    /*
    @RequestMapping("/getQuestions")
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
    }*/


    @RequestMapping(value = "/getQuestions", method = {RequestMethod.GET, RequestMethod.POST},
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getQuestions(int userId, int offset, int limit) {
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
        String jsonStr = JSON.toJSONString(vos);
        return jsonStr;
    }

}
