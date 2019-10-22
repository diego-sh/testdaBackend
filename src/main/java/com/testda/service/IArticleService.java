package com.testda.service;

import com.testda.model.Article;
import com.testda.util.CRUD;

public interface IArticleService extends CRUD<Article> {

	public Article controllerStock(Article article, Integer quantity);
}
