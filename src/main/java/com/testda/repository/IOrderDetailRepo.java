package com.testda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testda.model.OrderDetail;

public interface IOrderDetailRepo extends JpaRepository<OrderDetail, Integer> {

}
