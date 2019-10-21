package com.testda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.testda.model.Article;
import com.testda.model.Client;
import com.testda.model.OrderProduct;
import com.testda.model.OrderDetail;
import com.testda.service.IArticleService;
import com.testda.service.IClientService;
import com.testda.service.IOrderProductService;


@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableTransactionManagement
public class SpringBootTest {

	@Autowired
	private IClientService clientService;
	
	@Autowired
	private IArticleService articleService;
	
	@Autowired
	private IOrderProductService orderService;
	
	

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTest.class, args);
	}
	
	/*
	 * Método para cargar la data en la base de datos
	 * */
	@Bean
	InitializingBean sendDatabase() {
		return () -> {

			clientService.create(new Client("1723535553", "Diego", "Alpala", "diegoalpala91@gmail.com"));
			clientService.create(new Client("1721870002", "Gabriela", "Cedillo", "gdc@gmail.com"));
			clientService.create(new Client("1001656659", "Pablo", "Neruda", "pabloN@gmail.com"));
			clientService.create(new Client("1708713456", "Jesus", "Pachacama", "jpacha@gmail.com"));
			clientService.create(new Client("1723457773", "Paola", "Carrillo", "pcarrillo@gmail.com"));
			
			articleService.create(new Article("ART1", "Televisor 55'", 1200.00, 20));
			articleService.create(new Article("ART2", "Refrigeradora LG", 700.50, 30));
			articleService.create(new Article("ART3", "Microondas Indurama", 300.99, 10));
			articleService.create(new Article("ART4", "Plancha Oster", 60.20, 60));
			articleService.create(new Article("ART5", "Laptop Dell 17'", 1300.50, 40));
			articleService.create(new Article("ART6", "Batidora", 45.00, 10));
			articleService.create(new Article("ART7", "Equipo de Sonido", 1200.00, 50));
			
			List<Article> listArticle = articleService.findAll();
			List<Client> listClient = clientService.findAll();
			
			List<OrderDetail> detail = new ArrayList<>();
			List<OrderProduct> listOrder= new ArrayList<>();
			
			OrderDetail od = new OrderDetail();			
			od.setArticle(listArticle.get(6));
			od.setQuantity(1);
			detail.add(od);
			OrderDetail od2 = new OrderDetail();
			od2.setArticle(listArticle.get(0));
			od2.setQuantity(1);
			detail.add(od2);
			
			OrderProduct order = new OrderProduct();
			order.setDate(LocalDate.now());
			order.setDetail(detail);
			
			listOrder.add(order);			
			Client client=listClient.get(0);
			client.setListOrder(listOrder);
			clientService.update(client);

		};

	}

}
