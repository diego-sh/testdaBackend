package com.testda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testda.model.Client;

@Repository
public interface IClientRepo extends JpaRepository<Client, Integer>{

}
