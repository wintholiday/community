package com.example.community.mapper;

import com.example.community.pojo.User;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    // 查询所有用户信息
    @Select("select * from User")
    List<User> findAll();

    // 根据用户ID查询用户名
    @Select("select username from User WHERE userId = #{userId}")
    String findUsernameByUserid(int userid);

    // 根据用户名查询用户信息
    @Select("select * from User where username = #{username}")
    User findByUsername(String username);

    // 根据用户ID查询用户信息
    @Select("select * from User where userId = #{userId}")
    User findByUserid(int userId);

    // 根据用户名查询用户ID
    @Select("select userId from User where username = #{username}")
    int findIdByUsername(String username);

    // 根据用户名查询用户密码
    @Select("select password from User where username = #{username}")
    String findPasswordByUsername(String username);

    // 根据用户ID查询用户头像
    @Select("select `avatar` from User where userId = #{userId}")
    String findAvatarByUserid(int userId);

    // 插入用户信息
    @Update("INSERT INTO `User` (`username`, `password`) VALUES (#{username},#{password}); ")
    @Transactional
    void insertUser(User user);

    // 更新用户密码
    @Update("UPDATE `User` SET `password` = #{newPassword} WHERE `userId` = #{userId}; ")
    void updatePassword(String newPassword, int userId);

    // 更新用户名
    @Update("UPDATE `User` SET `username` = #{newUsername} WHERE `userId` = #{userId}; ")
    void updateUsername(String newUsername, int userId);

    // 更新用户头像
    @Update("UPDATE `User` SET `avatar` = #{avatar} WHERE `userId` = #{userId}; ")
    void updateAvatar( int userId,String avatar);
}
