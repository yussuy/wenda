package me.yushuo.wenda.controller;

import me.yushuo.wenda.model.Question;
import me.yushuo.wenda.model.ViewObject;
import me.yushuo.wenda.service.QuestionService;
import me.yushuo.wenda.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/index", "/"})
    public String index(Model model) {
        List<Question> questionList = questionService.getLatestQuestions(0, 0, 10);
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
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "index";
    }

//    @RequestMapping(value = "/user/{id}")
//    public String
}
