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
    @Update("INSERT INTO `Comment` (`commentId`, `isSubComment`,`date`, `userId`, `articleId`, `content`) " +
            "VALUES (#{commentId}, 0 ,#{date}, #{userId}, #{articleId}, #{content}); ")
    void commentToArticle(Long commentId, String date, int userId, int articleId, String content);


    // 在评论下发表回复
    @Update("INSERT INTO `Comment` (`commentId`, `isSubComment`,`date`, `userId`, `articleId`,`fatherCommentId`, `content`) " +
            "VALUES (#{commentId}, 1,#{date}, #{userId}, #{articleId},#{fatherCommentId}, #{content}); ")
    void commentToComment(Long commentId, String date, int userId, int articleId, long fatherCommentId, String content);

    // 根据评论ID查询评论
    @Select("SELECT * FROM `Comment` WHERE commentId = #{commentId};")
    Comment findCommentById(long commentId);

    // 根据文章ID查询文章下的评论
    @Select("SELECT `commentId`,`date`,`userId`,`articleId`,`fatherCommentId`,`content` FROM `Comment` WHERE `articleId` = #{articleId} AND `isSubComment` = 0;")
    List<Comment> findCommentByArticleId(int articleId);

    // 根据评论ID查询该评论下的回复
    @Select("SELECT `commentId`,`date`,`userId`,`articleId`,`fatherCommentId`,`content` FROM `Comment` WHERE `commentId` = #{commentId} AND `isSubComment` = 0;")
    List<Comment> findSubCommentByArticleId(long commentId);
}
