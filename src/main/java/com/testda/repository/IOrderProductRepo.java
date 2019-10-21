package com.testda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testda.model.OrderProduct;


public interface IOrderProductRepo extends JpaRepository<OrderProduct, Integer>{

}
