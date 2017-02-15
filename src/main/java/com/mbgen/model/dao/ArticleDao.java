package com.mbgen.model.dao;

import org.apache.ibatis.annotations.Param;

public interface ArticleDao {

    public com.mbgen.model.pojo.ArticleDo selectArticleByArticleId(@Param("articleId") int articleId);

    public java.util.List<com.mbgen.model.pojo.ArticleDo> selectAllArticleInfo();

    public java.util.List<com.mbgen.model.pojo.ArticleDo> selectArticleByArticleType(@Param("articleType") java.lang.String articleType);

    public void UpdateArticleByArticleId(@Param("article") com.mbgen.model.pojo.ArticleDo article);

    public void insertArticle(@Param("article") com.mbgen.model.pojo.ArticleDo article);

    public void deleteArticleByArticleId(@Param("articleId") int articleId);

}