package com.example.community.service;

import com.example.community.mapper.ArticleMapper;
import com.example.community.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional

@Component
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public void increaseClickCount(int articleId) {
        articleMapper.increaseClickCount(articleId);
    }

    public int getClickCount(int articleId) {
        return articleMapper.getClickCount(articleId);
    }

    public List<Article> getLikedArticlesByUserId(int userId) {
        return articleMapper.selectLikedArticlesByUserId(userId);
    }

    public Article getArticleById(int articleId) {
        return articleMapper.getArticleById(articleId);
    }
}
