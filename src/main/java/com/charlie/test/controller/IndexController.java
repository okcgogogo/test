package com.charlie.test.controller;

import com.charlie.test.mapper.UserMapper;
import com.charlie.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cooki:cookies) {
                if ("token".equals(cooki.getName())){
                    String value = cooki.getValue();
                    User user = userMapper.getUserByToken(value);
                    if (user != null) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
