package com.testda.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testda.model.OrderProduct;
import com.testda.repository.IOrderProductRepo;
import com.testda.service.IOrderProductService;

@Service
public class OrderProductServiceImp implements IOrderProductService {

	@Autowired
	private IOrderProductRepo orderRepo;
	
	@Override
	public List<OrderProduct> findAll() {
		return this.orderRepo.findAll();
	}

	@Override
	public OrderProduct create(OrderProduct order) {
		return this.orderRepo.save(order);
	}

	@Override
	public OrderProduct findById(OrderProduct order) {
		Optional<OrderProduct> o = this.orderRepo.findById(order.getId());
		if(o.isPresent())
			return o.get();
		else 
			return null;	
	}

	@Override
	public OrderProduct update(OrderProduct order) {
		return this.orderRepo.save(order);
	}

	@Override
	public void delete(String idOrder) {		
		this.orderRepo.deleteById(Integer.parseInt(idOrder));		
	}

}
