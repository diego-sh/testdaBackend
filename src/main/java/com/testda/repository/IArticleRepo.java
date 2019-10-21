package com.testda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testda.model.Article;

@Repository
public interface IArticleRepo extends JpaRepository<Article, String>{

}
