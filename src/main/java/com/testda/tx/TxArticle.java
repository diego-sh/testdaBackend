package com.testda.tx;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testda.model.Article;
import com.testda.model.Client;
import com.testda.service.IArticleService;
import com.testda.util.Crypto;
import com.testda.util.WebRequest;
import com.testda.util.WebResponse;
import com.testda.util.WebResponseMessage;

@Component
public class TxArticle {

	public static final String TX_NAME_ArticleSave = "ArticleSave";
	public static final String TX_CODE_ArticleSave = "TxQQRArticleSave";
	
	public static final String TX_NAME_GetAllArticles = "GetAllArticles";
	public static final String TX_CODE_GetAllArticles = "TxQQRGetAllArticles";
	
	public static final String TX_NAME_DeleteArticle = "DeleteArticle";
	public static final String TX_CODE_DeleteArticle = "TxQQRDeleteArticle";

	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	
	@Autowired
	private IArticleService articleService;
	
	/**
	 * TX NAME: ArticleSave: Guarda/actualiza el artículo
	 * 
	 */
	public ResponseEntity<Object> TxQQRArticleSave(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_ArticleSave);
		wrei.setTransactionCode(TX_CODE_ArticleSave);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {
				Article article = JSON_MAPPER.readValue(jsonValue, Article.class);
				article = this.articleService.create(article);

				if (article != null) {
					String json = JSON_MAPPER.writeValueAsString(article);
					String jsonCryp = Crypto.encrypt(json);
					wrei.setParameters(jsonCryp);
					wrei.setStatus(WebResponseMessage.STATUS_OK);
					wrei.setMessage(WebResponseMessage.CREATE_ORDER_OK);
					return new ResponseEntity<Object>(wrei, HttpStatus.OK);

				} else {
					wrei.setMessage(WebResponseMessage.CREATE_ORDER_ERROR);
					wrei.setStatus(WebResponseMessage.STATUS_ERROR);
					return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (IOException e) {
				wrei.setMessage(WebResponseMessage.ERROR_TO_CLASS);
				wrei.setStatus(WebResponseMessage.STATUS_ERROR);
				return new ResponseEntity<Object>(wrei, HttpStatus.BAD_REQUEST);
			}
		}

	}
	
	/**
	 * TX NAME: GetAllClient: consulta todos los clientes.
	 * 
	 */
	public ResponseEntity<Object> TxQQRGetAllArticle(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_GetAllArticles);
		wrei.setTransactionCode(TX_CODE_GetAllArticles);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {

				List<Article> listArticle = this.articleService.findAll();

				if (listArticle != null || !listArticle.isEmpty()) {
					String json = JSON_MAPPER.writeValueAsString(listArticle);
					String jsonCryp = Crypto.encrypt(json);
					wrei.setParameters(jsonCryp);
					wrei.setStatus(WebResponseMessage.STATUS_OK);
					wrei.setMessage(WebResponseMessage.OBJECT_FIND_OK);
					return new ResponseEntity<Object>(wrei, HttpStatus.OK);

				} else {
					wrei.setMessage(WebResponseMessage.OBJECT_FIND_EMPTY);
					wrei.setStatus(WebResponseMessage.STATUS_INFO);
					return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (IOException e) {
				wrei.setMessage(WebResponseMessage.ERROR_TO_CLASS);
				wrei.setStatus(WebResponseMessage.STATUS_ERROR);
				return new ResponseEntity<Object>(wrei, HttpStatus.BAD_REQUEST);
			}
		}

	}

	/**
	 * TX NAME: DeleteClient: Elimina un cliente determinado.
	 * 
	 */
	public ResponseEntity<Object> TxQQDeleteArticle(WebRequest wri) {

		WebResponse wrei = new WebResponse();
		wrei.setTransactionName(TX_NAME_DeleteArticle);
		wrei.setTransactionCode(TX_CODE_DeleteArticle);

		String jsonValue = Crypto.decrypt(wri.getParameters());
		if (jsonValue.equals(Crypto.ERROR)) {
			wrei.setMessage(WebResponseMessage.ERROR_DECRYPT);
			wrei.setStatus(WebResponseMessage.STATUS_ERROR);
			return new ResponseEntity<Object>(wrei, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {
				Article article = JSON_MAPPER.readValue(jsonValue, Article.class);
				this.articleService.delete(article.getId());				
				wrei.setStatus(WebResponseMessage.STATUS_OK);
				wrei.setMessage(WebResponseMessage.OBJECT_DELETED);
				return new ResponseEntity<Object>(wrei, HttpStatus.OK);
				
			} catch (IOException e) {
				wrei.setMessage(WebResponseMessage.ERROR_TO_CLASS);
				wrei.setStatus(WebResponseMessage.STATUS_ERROR);
				return new ResponseEntity<Object>(wrei, HttpStatus.BAD_REQUEST);
			}
		}

	}


}
