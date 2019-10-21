package com.testda.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testda.model.Article;
import com.testda.model.Client;
import com.testda.repository.IArticleRepo;
import com.testda.service.IArticleService;

@Service
public class ArticleServiceImp implements IArticleService {

	@Autowired
	private IArticleRepo articleRepo;
	
	@Override
	public List<Article> findAll() {
		return this.articleRepo.findAll();
	}

	@Override
	public Article create(Article article) {
		return this.articleRepo.save(article);
	}

	@Override
	public Article findById(Article article) {
		Optional<Article> a = this.articleRepo.findById(article.getId());
		if(a.isPresent())
			return a.get();
		else 
			return null;	
	}

	@Override
	public Article update(Article article) {
		return this.articleRepo.save(article);
	}

	@Override
	public void delete(String id) {
		this.articleRepo.deleteById(id);
	}

}
