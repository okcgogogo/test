package com.charlie.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.charlie.test.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        JSONObject obj = new JSONObject();
        obj.put("client_id", clientID);
        obj.put("client_secret", clientSecret);
        obj.put("code", code);
        obj.put("redirect_uri", redirectUri);
        obj.put("state", state);
        String jsonInput = JSON.toJSONString(obj);
        String accessToken = githubProvider.getAccessToken(jsonInput);
        JSONObject user = githubProvider.getUser(accessToken);
        System.out.println(user.getString("name"));
        return "index";
    }
}
