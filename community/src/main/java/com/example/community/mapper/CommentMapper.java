package com.example.community.mapper;

import com.example.community.pojo.Comment;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
@Resource
public interface CommentMapper {

    // 在文章下发表评论
    @Update("INSERT INTO `Comment` (`id`, `isSubComment`,`date`, `userId`, `articleId`, `context`) " +
            "VALUES (#{id}, 0 ,#{date}, #{userId}, #{articleId}, #{context}); ")
    void commentToArticle(Long id, String date, int userId, int articleId, String context);

    // 在评论下发表回复
    @Update("INSERT INTO `Comment` (`id`, `isSubComment`,`date`, `userId`, `articleId`,`fatherCommentId`, `context`) " +
            "VALUES (#{id}, 1,#{date}, #{userId}, #{articleId},#{fatherCommentId}, #{context}); ")
    void commentToComment(Long id, String date, int userId, String articleId, String fatherCommentId, String context);

    // 根据评论ID查询评论
    @Select("SELECT * FROM `Comment` WHERE id = #{id};")
    Comment findCommentById(String id);

    // 根据文章ID查询文章下的评论
    @Select("SELECT `id`,`date`,`userId`,`articleId`,`fatherCommentId`,`context` FROM `Comment` WHERE `articleId` = #{articleId} AND `isSubComment` = 0;")
    List<Comment> findCommentByArticleId(int articleId);

    // 根据评论ID查询该评论下的回复
    @Select("SELECT `id`,`date`,`userId`,`articleId`,`fatherCommentId`,`context` FROM `Comment` WHERE `id` = #{commentId} AND `isSubComment` = 0;")
    List<Comment> findSubCommentByArticleId(String commentId);
}
