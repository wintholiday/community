package com.example.community.controller;

import com.example.community.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    /*@Autowired
    private FavoriteMapper favoriteMapper;

    @PostMapping("")
    public Map<String, Object> favorite(Integer articleId, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        Favorite favorite = favoriteMapper.select(articleId, userId);
        if (favorite != null) {
            favoriteMapper.delete(articleId, userId);
            map.put("code", 0);
            map.put("msg", "取消收藏成功");
        } else {
            favoriteMapper.insert(new Favorite(null, articleId, userId));
            map.put("code", 1);
            map.put("msg", "收藏成功");
        }
        return map;
    }*/
}

