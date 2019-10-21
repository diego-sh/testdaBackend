package com.testda.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testda.model.Client;
import com.testda.repository.IClientRepo;
import com.testda.service.IClientService;

@Service
public class ClientServiceImp implements IClientService {

	@Autowired
	private IClientRepo clientRepo;

	@Override
	public List<Client> findAll() {		
		return this.clientRepo.findAll();
	}

	
	@Override
	public Client create(Client client) {
		return this.clientRepo.save(client);
	}

	@Override
	public Client findById(Client client) {
		Optional<Client> c = this.clientRepo.findById(client.getId());
		if(c.isPresent())
			return c.get();
		else 
			return null;		
	}

	@Override
	public Client update(Client obj) {		
		return  this.clientRepo.save(obj);
	}

	@Override
	public void delete(String id) {		
		this.clientRepo.deleteById(Integer.parseInt(id));
	}

}
