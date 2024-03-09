package com.example.community.service;

import com.example.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
public class UsersService {

    @Autowired
    private UserMapper userMapper;

    public void updateAvatar( int userId,String avatar) {
        userMapper.updateAvatar( userId,avatar);
    }

    public String getAvatarUrlByUserId(int userId){
         String url=userMapper.getAvatarUrlByUserId(userId);
         return url;
    }

    /**
     * 根据用户名获取用户ID。
     *
     * @param username 用户名
     * @return 用户ID，如果用户不存在，则返回null。
     */
    public Integer getUserIdByUsername(String username) {
        // 调用Mapper方法
        Integer userId = userMapper.findIdByUsername(username);
        if (userId != null) {
            // 用户存在，返回用户ID
            return userId;
        } else {
            // 用户不存在，可以根据业务需求返回null或抛出异常
            return 1; // 或者抛出一个自定义异常
        }
    }



}
