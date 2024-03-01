package com.example.community.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.community.service.LikesService;
import com.example.community.service.ArticleService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.community.pojo.Result;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.LikeMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.pojo.Article;
import com.example.community.pojo.Comment;
import com.example.community.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CommentMapper commentMapper;

    @PostMapping("/write-article")
    public Result writeArticle(@RequestBody String json){
        log.info("接收到写文章的请求");
        UserDetails userDetails = null;
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            return Result.error("用户错误");
        }*/

        /*Article article = JSONUtil.toBean(json, Article.class);
        User user = userMapper.findByUsername("username"); // 替换为实际的用户名
        articleMapper.insertArticle(user.getUserId(), article.getTitle(), DateUtil.today(), article.getContext());
        int articleId = articleMapper.findUserArticleIdByUserId(user.getUserId());*/

        Article article = JSONUtil.toBean(json, Article.class);
        User user = userMapper.findByUsername(userDetails.getUsername());
        articleMapper.insertArticle(user.getUserId(), article.getTitle(), DateUtil.today(), article.getContext());
        int articleId = articleMapper.findUserArticleIdByUserId(user.getUserId());

        log.info("文章写入成功，ID：{}", articleId);
        return Result.success();
    }

    @Autowired
    private LikesService likeService;
    @PostMapping("/{articleId}/like")
    public Result like(@PathVariable("articleId") int articleId, @RequestParam("userId") int userId) {
        log.info("接收到点赞文章的请求，文章ID：{} 用户ID: {}", articleId, userId);

        // 调用点赞逻辑方法
        likeService.likeArticle(articleId, userId);

        log.info("文章点赞成功");
        return Result.success();
    }

    @PostMapping("/{articleId}/cancelLike")
    public Result cancelLike(@PathVariable("articleId") int articleId, @RequestParam("userId") int userId) {
        log.info("接收到取消点赞文章的请求，文章ID：{} 用户ID: {}", articleId, userId);

        // 调用取消点赞逻辑方法
        likeService.cancelLike(articleId, userId);

        log.info("取消点赞成功");
        return Result.success();
    }

    @Autowired
    private ArticleService clickService;

    @PutMapping("/{articleId}/click")
    public ResponseEntity<String> increaseClickCount(@PathVariable int articleId) {
        clickService.increaseClickCount(articleId);
        return ResponseEntity.ok("文章id为" + articleId+"点击量增加");
    }

    @GetMapping("/{articleId}/clickCount")
    public ResponseEntity<Integer> getClickCount(@PathVariable int articleId) {
        int clickCount = clickService.getClickCount(articleId);
        return ResponseEntity.ok(clickCount);
    }



    @GetMapping("/get/{articleId}")
    public Result getArticle(@PathVariable int articleId){
        log.info("接收到获取文章的请求，文章ID：{}", articleId);

        Article article = articleMapper.findArticleByArticleId(articleId);
        article.setArticleId(articleId);

        List<Comment> comments = commentMapper.findCommentByArticleId(articleId);
        for(Comment comment : comments) {
            comment.setUsername(userMapper.findUsernameByUserid(comment.getUserId()));
            comment.setSubComments(commentMapper.findSubCommentByArticleId(comment.getId()));
            for(Comment subComment : comment.getSubComments()) {
                subComment.setUsername(userMapper.findUsernameByUserid(subComment.getUserId()));
            }
        }

        article.setComments(comments);
        article.setUsername(userMapper.findByUserid(article.getUserId()).getUsername());

        article.setLikeCount(likeService.countLikeByArticle(articleId)); // 实际点赞数查询逻辑
        article.setClickCount(clickService.getClickCount(articleId)); // 实际点击数查询逻辑

        log.info("成功获取文章");
        return Result.success(article);
    }

    @GetMapping("/user/{userId}")
    public Result getUserArticle(@PathVariable int userId){
        log.info("接收到获取用户文章的请求，用户ID：{}", userId);

        String username = userMapper.findUsernameByUserid(userId);
        List<Article> articles = articleMapper.findArticleWithoutContextByUsername(userId);
        for(Article article : articles){
            int articleId = article.getArticleId();
            article.setLikeCount(likeService.countLikeByArticle(articleId)); // 实际点赞数查询逻辑
            article.setClickCount(clickService.getClickCount(articleId)); // 实际点击数查询逻辑
            article.setUsername(username);
        }

        log.info("成功获取用户文章");
        return Result.success(articles);
    }


}