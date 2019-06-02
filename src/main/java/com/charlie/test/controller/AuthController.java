package com.charlie.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.charlie.test.mapper.UserMapper;
import com.charlie.test.model.User;
import com.charlie.test.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *
 */

@Controller
public class AuthController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client_id}")
    private String clientID;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                          HttpServletResponse response) {
        JSONObject obj = new JSONObject();
        obj.put("client_id", clientID);
        obj.put("client_secret", clientSecret);
        obj.put("code", code);
        obj.put("redirect_uri", redirectUri);
        obj.put("state", state);
        String jsonInput = JSON.toJSONString(obj);
        String accessToken = githubProvider.getAccessToken(jsonInput);
        JSONObject user = githubProvider.getUser(accessToken);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            // 入库保存user
            User user1 = new User();
            user1.setAccount_id(user.getString("id"));
            user1.setName(user.getString("name"));
            user1.setToken(token);
            user1.setGmt_create(System.currentTimeMillis());
            user1.setGmt_modified(System.currentTimeMillis());
            user1.setAvatarUrl(user.getString("avatar_url"));
            userMapper.insertUser(user1);
            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);
            return "redirect:/";
        }else {
            // 登录失败 跳转首页
            return "redirect:/";
        }
    }
}
