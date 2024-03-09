package com.example.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private long commentId;//评论id，唯一
    private List<Comment> subComments;//子评论
    private String date;//评论日期
    private int userId;//评论用户
    private int articleID;//所属文章id
    private int fatherCommentId;//父评论id（如果为子评论）
    private int isSubComment;//是否为子评论，默认false
    private String context;//评论内容
    private String username;//用户名
}

