package com.example.community.service;

import com.example.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
@Service
public class UsersService {

    @Autowired
    private UserMapper userMapper;

    public void updateAvatar( int userId,String avatar) {
        userMapper.updateAvatar( userId,avatar);
    }
}
