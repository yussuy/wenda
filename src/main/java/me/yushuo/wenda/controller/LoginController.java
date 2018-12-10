package me.yushuo.wenda.controller;

import me.yushuo.wenda.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;

    @RequestMapping("/reglogin")
    public String reglogin() {
        return "login";
    }

    @RequestMapping(value = "/reg", method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password) {
        try {
            Map<String, Object> map = loginService.registry(username, password);
            if (!map.containsKey("msg")) {
                return "redirect:/";
            } else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            model.addAttribute("msg", "服务器出错");
            return "login";
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password) {
        try {
            Map<String, Object> map = loginService.login(username, password);
            if (!map.containsKey("msg")) {
                return "redirect:/";
            } else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            model.addAttribute("msg", "服务器出错");
            return "login";
        }
    }
}
