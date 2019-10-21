package com.testda.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLI_ID")
	private Integer id;

	@Column(name = "CLI_CI", nullable = false)
	private String CI;

	@Column(name = "CLI_NAME", nullable = false, length = 256)
	private String name;

	@Column(name = "CLI_LAST_NAME", nullable = false, length = 256)
	private String lastName;

	@Column(name = "CLI_EMAIL", nullable = true)
	private String email;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "CLI_ID", nullable = true)
	private List<OrderProduct> listOrder;

	public Client() {};
	
	public Client(String ci, String name, String lastName, String email) {
		this.CI = ci;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCI() {
		return CI;
	}

	public void setCI(String cI) {
		CI = cI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<OrderProduct> getListOrder() {
		return listOrder;
	}

	public void setListOrder(List<OrderProduct> listOrder) {
		this.listOrder = listOrder;
	}

	

}
