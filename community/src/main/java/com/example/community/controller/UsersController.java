package com.example.community.controller;

import com.example.community.service.ArticleService;
import com.example.community.service.LikesService;
import com.example.community.utils.JwtUtils;
import com.example.community.pojo.Result;
import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.pojo.Article;
import com.example.community.pojo.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UsersController {
    @Resource
    @Autowired
    UserMapper userMapper;
    @Autowired
    private LikesService likesService;
    @Autowired
    private ArticleService clickService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    ArticleMapper articleMapper;
    /*//登录
    @PostMapping("/login/")
    public Result loginUser(@RequestParam String username,@RequestParam String password) {
        if(Objects.equals(password, userMapper.findPasswordByUsername(username))){
            Map<String, String> tokenMap = new HashMap<>();
            String token = jwtUtils.generateToken(username);
            tokenMap.put("token",token);
            tokenMap.put("tokenHead",tokenHead);
            return Result.success(tokenMap);
        }else{
            return Result.error("用户名或密码错误");
        }
    }*/
    // 登录
    @PostMapping("/login")
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

    @PostMapping("/register/")
    public Result register(@RequestParam String username,@RequestParam String password){
        log.info("尝试注册用户名 {}", username);
        if(userMapper.findByUsername(username) != null){
            return Result.error("用户名已存在");
        }else{
            userMapper.insertUser(new User(username,password));
            return Result.success(null);
        }
    }

    @PostMapping("/change-username")
    public Result changeUsername(@RequestParam String newUsername){
        log.info("尝试修改用户名为: {}", newUsername);
        UserDetails userDetails = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            return Result.error("error");
        }
        User user = userMapper.findByUsername(userDetails.getUsername());
        if(Objects.equals(newUsername,user.getUsername())){
            return Result.error("新用户名不能与旧用户名相同");
        } else if (userMapper.findByUsername(newUsername) != null) {
            return Result.error("用户名已存在");
        }
        userMapper.updateUsername(newUsername,user.getUserId());
        return Result.success(null);
    }


    @PostMapping("/change-password")
    public Result changePassword(@RequestParam String newPassword){
        log.info("尝试修改密码");
        UserDetails userDetails = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            return Result.error("error");
        }
        User user = userMapper.findByUsername(userDetails.getUsername());
        if(Objects.equals(newPassword,user.getPassword())){
            return Result.error("新密码不能与旧密码相同");
        }
        userMapper.updatePassword(newPassword,user.getUserId());
        return Result.success(null);
    }

    @GetMapping("/like")
    public Result getLike() {
        log.info("获取喜欢的文章");
        UserDetails userDetails = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            return Result.error("error");
        }

        int userId = userMapper.findIdByUsername(userDetails.getUsername());
        List<Integer> likedArticleIds = likesService.getLikedArticleIdsByUserId(userId);

        List<Article> articles = new ArrayList<>();
        for (Integer articleId : likedArticleIds) {
            Article article = articleService.getArticleById(articleId);
            if (article != null) {
                article.setLikeCount(likesService.countLikeByArticle(articleId)); // 实际点赞数查询逻辑
                article.setClickCount(clickService.getClickCount(articleId)); // 实际点击数查询逻辑
                article.setUsername(userMapper.findUsernameByUserid(article.getUserId()));
                articles.add(article);
            }
        }

        return Result.success(articles);
    }




}
