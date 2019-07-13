package com.charlie.test.service;

import com.charlie.test.mapper.UserMapper;
import com.charlie.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdateUser(User user){
        User userDB = userMapper.getUserByAccountId(user.getAccount_id());
        if (userDB == null) {
            userMapper.insertUser(user);
        }else {
            userDB.setAvatarUrl(user.getAvatarUrl());
            userDB.setName(user.getName());
            userDB.setToken(user.getToken());
            userDB.setGmt_modified(System.currentTimeMillis());
            userMapper.updateUser(userDB);
        }
    }
}
