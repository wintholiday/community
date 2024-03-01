package com.example.community.controller;

import com.example.community.mapper.ArticleMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.pojo.Article;
import com.example.community.pojo.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/home")
public class HomePage {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/latest-articles")
    //按照发布时间降序
    public Result latestArticles(@RequestParam int offset){
        List<Article> articles = articleMapper.findArticleOrderByDate(offset);
        for(Article article: articles){
            article.setUsername(userMapper.findUsernameByUserid(article.getUserId()));
        }
        return Result.success(articles);
    }


    @GetMapping("/articles-by-date")
    public Result getArticlesOrderByDate(@RequestParam int pageNum, @RequestParam int pageSize) {
        // 开启分页，pageNum为第几页，pageSize为每页的大小
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.findArticleOrderByDate();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return Result.success(pageInfo);
    }


    @GetMapping("/articles-by-clickCount")
    public Result getArticlesOrderByClickCount(@RequestParam int pageNum, @RequestParam int pageSize) {
        // 开启分页，pageNum为第几页，pageSize为每页的大小
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.findArticleOrderByClickCount();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return Result.success(pageInfo);
    }





}
