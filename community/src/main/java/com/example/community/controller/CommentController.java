package com.example.community.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.pojo.Result;
import com.example.community.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.community.utils.SnowflakeIdGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;


    @PostMapping("/{articleId}/comment")
    //对文章评论
    public Result commentToArticle(@PathVariable int articleId,
                                      @RequestBody String json){
        if(articleMapper.findArticleByArticleId(articleId) == null){
            return Result.error("文章id错误");
        }
        String context = JSONUtil.parseObj(json).getStr("context");
        UserDetails userDetails = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            return Result.error("error");
        }
        User user = userMapper.findByUsername(userDetails.getUsername());
        commentMapper.commentToArticle(new SnowflakeIdGenerator().nextId(),
                DateUtil.today(), user.getUserId(),
                articleId, context
        );

        log.info("评论创建成功");
        return Result.success();
    }

    @PostMapping("/{articleId}/{commentId}/comment")
    //对文章评论
    public Result commentToComment(@PathVariable String articleId,
                                      @PathVariable String commentId,
                                      @RequestBody String json){
        if(articleMapper.findArticleByArticleId(Integer.parseInt(articleId)) == null){
            log.error("文章id错误");
            return Result.error("文章id错误");
        }
        if (commentMapper.findCommentById(commentId) == null){
            log.error("评论id错误");
            return Result.error("文章id错误");
        }
        String context = JSONUtil.parseObj(json).getStr("context");
        UserDetails userDetails = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            return Result.error("error");
        }
        User user = userMapper.findByUsername(userDetails.getUsername());
        commentMapper.commentToComment(new SnowflakeIdGenerator().nextId(),
                DateUtil.today(), user.getUserId(),
                articleId,commentId, context
        );

        log.info("评论创建成功");
        return Result.error(null);
    }
}

