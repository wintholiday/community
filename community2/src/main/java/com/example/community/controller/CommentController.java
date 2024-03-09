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
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;*/
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.example.community.utils.SnowflakeIdGenerator;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Transactional
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
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;


    /*@PostMapping("/{articleId}/comment")
    //对文章评论
    public Result commentToArticle(@PathVariable int articleId,
                                      @RequestBody String json){
        if(articleMapper.findArticleByArticleId(articleId) == null){
            return Result.error("文章id错误");
        }
        String context = JSONUtil.parseObj(json).getStr("content");
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
        String context = JSONUtil.parseObj(json).getStr("content");
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
    }*/

    // 处理对文章的评论
    @PostMapping("/{articleId}/comment")
    public Result commentToArticle(
            // 文章ID通过URL路径传入
            @PathVariable int articleId,
            // 评论内容通过请求体传入
            @RequestBody String json,
            // 用户名通过请求头传入
            @RequestHeader("username") String username
    ) {
        // 检查文章是否存在
        if (articleMapper.findArticleByArticleId(articleId) == null) {
            return Result.error("文章id错误");
        }
        // 解析评论内容
        String context = JSONUtil.parseObj(json).getStr("content");
        // 通过用户名查找用户
        User user = userMapper.findByUsername(username);

        // 如果用户不存在，返回错误
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 调用commentMapper创建评论
        commentMapper.commentToArticle(
                // 使用Snowflake算法生成的ID
                snowflakeIdGenerator.nextId(),
                // 当前日期
                DateUtil.today(),
                // 用户ID
                user.getUserId(),
                // 文章ID
                articleId,
                // 评论内容
                context
        );

        // 日志记录评论创建成功
        log.info("评论创建成功");
        // 返回成功结果
        return Result.success();
    }

    // 处理对评论的回复
    @PostMapping("/{articleId}/{commentId}/comment")
    public Result commentToComment(
            // 文章ID通过URL路径传入
            @PathVariable int articleId,
            // 父评论ID通过URL路径传入
            @PathVariable long commentId,
            // 评论内容通过请求体传入
            @RequestBody String json,
            // 用户名通过请求头传入
            @RequestHeader("username") String username
    ) {
        // 检查文章是否存在
        if (articleMapper.findArticleByArticleId(articleId) == null) {
            log.error("文章id错误");
            return Result.error("文章id错误");
        }
        // 检查父评论是否存在
        if (commentMapper.findCommentById(commentId) == null) {
            log.error("评论id错误");
            return Result.error("评论id错误");
        }
        // 解析评论内容
        String context = JSONUtil.parseObj(json).getStr("content");
        // 通过用户名查找用户
        User user = userMapper.findByUsername(username);

        // 如果用户不存在，返回错误
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 调用commentMapper创建对评论的回复
        commentMapper.commentToComment(
                // 使用Snowflake算法生成的ID
                snowflakeIdGenerator.nextId(),
                // 当前日期
                DateUtil.today(),
                // 用户ID
                user.getUserId(),
                // 文章ID
                articleId,
                // 父评论ID
                commentId,
                // 评论内容
                context
        );

        // 日志记录评论创建成功
        log.info("评论创建成功");
        // 返回成功结果
        return Result.success();
    }
}

