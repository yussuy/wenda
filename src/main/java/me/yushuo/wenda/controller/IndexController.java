package me.yushuo.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(path = {"/index", "/"})
    @ResponseBody
    public String index(HttpSession session) {
        logger.info("VISIT HOME");
        return "hello " + session.getAttribute("name");
    }

    @RequestMapping(value = "/vm")
    public String vm() {
        return "hello";
    }

    @RequestMapping(value = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        return sb.toString();
    }

    @RequestMapping(value = {"/redirect"}, method = {RequestMethod.GET})
    public String redirect(HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session) {
        session.setAttribute("name", "YS");
        return "redirect:/";
    }

    //跳转可以通过user-agent跳转到PC和手机版
    @RequestMapping(value = {"/coderedirect/{code}"}, method = {RequestMethod.GET})
    public RedirectView coderedirect(HttpSession session, @PathVariable("code") int code) {
        RedirectView redirect = new RedirectView("/", true);
        session.setAttribute("name", "YS");
        if(code == 301) {//chrome network size 可以看出301 302区别
            redirect.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return redirect;
    }

    @RequestMapping(value = "/admin", method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return e.getMessage();
    }
}
