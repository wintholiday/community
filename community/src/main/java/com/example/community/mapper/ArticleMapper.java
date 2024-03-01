package com.example.community.mapper;

import com.example.community.pojo.Article;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
@Resource
public interface ArticleMapper {
    //发布文章
    @Update("INSERT INTO `Article` (`userId`, `title`, `date`, `context`) VALUES (#{userId}, #{title}, #{date}, #{context});")
    void insertArticle(int userId,String title,String date,String context);

    //通过id查询某个用户的最新文章
    @Select("SELECT MAX(articleId) FROM Article WHERE userId = #{userId}; ")
    int findUserArticleIdByUserId(int userId);

    //通过文章id查询文章
    @Select("SELECT `userId`,`title`,`date`,`context` FROM Article WHERE articleId = #{articleId}; ")
    Article findArticleByArticleId(int article);

    @Select("SELECT `userId`,`title`,`date` FROM Article WHERE articleId = #{articleId}; ")
    Article findArticleWithoutContextByArticleId(int article);


    @Select("SELECT `userId`,`title`,`date`,`articleId` FROM Article WHERE userId = #{userId}; ")
    List<Article> findArticleWithoutContextByUsername(int userId);

    @Select("SELECT userId, title, date, articleId FROM Article ORDER BY date DESC LIMIT #{offset}, 5;")
    List<Article> findArticleOrderByDate(int offset);

    //传入不同的 orderBy 值（'clickCount' 或 'date'）和 offset 值来实现按不同条件排序的文章列表查询
    List<Article> selectArticles(@Param("orderBy") String orderBy, @Param("offset") int offset);

    void increaseClickCount(@Param("articleId") int articleId);
    int getClickCount(@Param("articleId") int articleId);

    List<Article> selectLikedArticlesByUserId(@Param("userId") int userId);
    Article getArticleById(@Param("articleId") int articleId);

    List<Article> findArticleOrderByDate();
    List<Article> findArticleOrderByClickCount();


}
