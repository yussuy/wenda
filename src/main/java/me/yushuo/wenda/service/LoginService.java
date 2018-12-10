package me.yushuo.wenda.service;

import me.yushuo.wenda.dao.UserDAO;
import me.yushuo.wenda.model.User;
import me.yushuo.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.UserDataHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    UserDAO userDAO;

    public Map<String, Object> registry(String username, String password) {
        Map<String, Object> map = new HashMap();
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msg", "用户名已被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        String head = "https://avatars0.githubusercontent.com/u/" + new Random().nextInt(10000);
        user.setHeadUrl(head);
        userDAO.addUser(user);
        return map;

    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap();
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
        }

        User user = userDAO.selectByName(username);
        if (user == null) {
            map.put("msg", "用户未注册");
        }

        if(!user.getPassword().equals(WendaUtil.MD5(password + user.getSalt()))) {
            map.put("msg", "密码错误");
        }

        return map;
    }
}
