package me.yushuo.wenda.service;

import me.yushuo.wenda.dao.UserDAO;
import me.yushuo.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public User getUserByName(String name) {
        return  userDAO.selectByName(name);
    }
}
