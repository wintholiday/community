package com.example.community.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FavoriteMapper {
   /* @Select("select * from favorite where article_id=#{articleId} and user_id=#{userId}")
    Favorite select(Integer articleId, Integer userId);

    @Insert("insert into favorite(article_id,user_id) values(#{articleId},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "favoriteId", keyColumn = "favorite_id")
    void insert(Favorite favorite);

    @Delete("delete from favorite where article_id=#{articleId} and user_id=#{userId}")
    void delete(Integer articleId, Integer userId);*/
}
