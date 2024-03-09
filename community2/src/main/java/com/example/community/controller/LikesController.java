package com.example.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/likes")
public class LikesController {
   /* @Autowired
    private LikesMapper likesMapper;

    @PostMapping("")
    public Map<String, Object> like(Integer articleId, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        Like likes = likesMapper.select(articleId, userId);
        if (likes != null) {
            likesMapper.delete(articleId, userId);
            map.put("code", 0);
            map.put("msg", "取消点赞成功");
        } else {
            likesMapper.insert(new Lik(null, articleId, userId));
            map.put("code", 1);
            map.put("msg", "点赞成功");
        }
        return map;
    }*/
}
