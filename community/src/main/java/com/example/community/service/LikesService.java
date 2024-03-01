package com.example.community.service;

import com.example.community.mapper.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 点赞服务类，处理点赞相关业务逻辑
 */
@Service
public class LikesService {

    @Autowired
    private LikeMapper likeMapper;

    /**
     * 为文章点赞
     * @param articleId 文章ID
     * @param userId 文章ID
     */
    public void likeArticle(int articleId, int userId) {
        if (!hasLiked(articleId,userId)) {
            likeMapper.likeArticle(articleId, userId);
        }
    }

    /**
     * 取消对文章的点赞
     * @param articleId 文章ID
     * @param userId 文章ID
     */
    public void cancelLike(int articleId,int userId) {
        likeMapper.cancelLike(articleId, userId);
    }

    /**
     * 检查用户是否已经点赞过该文章
     * @param articleId 文章ID
     * @param userId 文章ID
     * @return true：已点赞，false：未点赞
     */
    public boolean hasLiked(int articleId,int userId) {
        return likeMapper.countLikeByUserAndArticle(articleId, userId) > 0;
    }

    public int countLikeByUserAndArticle(int articleId, int userId) {
        return likeMapper.countLikeByUserAndArticle(articleId, userId);
    }
    public int countLikeByArticle(int articleId) {
        return likeMapper.countLikeByArticle(articleId);
    }

    public List<Integer> getLikedArticleIdsByUserId(int userId) {
        return likeMapper.getLikedArticleIdsByUserId(userId);
    }
}
