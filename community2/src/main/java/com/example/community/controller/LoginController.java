package com.example.community.controller;

import com.example.community.mapper.UserMapper;
import com.example.community.pojo.LoginRequest;
import com.example.community.pojo.Result;
import com.example.community.pojo.User;
import com.example.community.service.UsersService;
import com.example.community.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {

    /*@Autowired
    private UsersService usersService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;
*/
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping("/login")
    public Result loginUser(@RequestParam String username, @RequestParam String password) {
        log.info("尝试使用用户名 {} 登录", username);
        User user = userMapper.findByUsername(username);
        if (user != null && Objects.equals(password, user.getPassword())) {
            String token = jwtUtils.generateToken(username);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            return Result.success(tokenMap);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
}

