package com.example.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 点赞操作的Mapper接口，定义了点赞相关的数据库操作
 */
@Mapper
public interface LikeMapper {

    /**
     * 为文章点赞
     * @param articleId 文章ID
     */
    void likeArticle(@Param("articleId") int articleId, @Param("userId") int userId);
    /**
     * 取消对文章的点赞
     * @param articleId 文章ID
     */
    void cancelLike(@Param("articleId") int articleId, @Param("userId") int userId);
    /**
     * 统计对指定文章的点赞数
     * @param articleId 文章ID
     * @return 点赞数
     */
    int countLikeByUserAndArticle(@Param("articleId") int articleId, @Param("userId") int userId);
    int countLikeByArticle(@Param("articleId") int articleId);


    List<Integer> getLikedArticleIdsByUserId(@Param("userId") int userId);
}
