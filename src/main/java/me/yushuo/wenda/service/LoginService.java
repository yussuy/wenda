package me.yushuo.wenda.service;

import me.yushuo.wenda.dao.LoginTicketDAO;
import me.yushuo.wenda.dao.UserDAO;
import me.yushuo.wenda.model.LoginTicket;
import me.yushuo.wenda.model.User;
import me.yushuo.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class LoginService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

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
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        userDAO.addUser(user);

        String ticket = addTicket(user.getId());
        map.put("ticket", ticket);

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
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user == null) {
            map.put("msg", "用户未注册");
            return map;

        }

        if (!user.getPassword().equals(WendaUtil.MD5(password + user.getSalt()))) {
            map.put("msg", "密码错误");
            return map;
        }

        String ticket = addTicket(user.getId());

        map.put("ticket", ticket);

        return map;
    }

    String addTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setTicket(UUID.randomUUID().toString());
        Date date = new Date();
        date.setTime(date.getTime() + 3600 * 24 * 1000);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        loginTicketDAO.addLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }
}
