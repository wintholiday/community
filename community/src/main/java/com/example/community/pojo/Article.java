package com.example.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int userId;//文章编写的用户
    private String username;//文章编写的用户名
    private int articleId;//文章id，唯一
    private String title;//文章标题
    private int clickCount;//点击次数
    private String date;//发表日期
    private List<Comment> comments;//评论
    private int likeCount;//点赞数
    private String context;//文章
}
