package com.example.community.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;//用户名
    private String password;//密码
    private int userId;//用户id，唯一,自增

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
