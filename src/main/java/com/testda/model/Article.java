package com.testda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class Article {

	@Id
	@Column(name = "ART_ID", nullable = false, length = 32)
	private String id;

	@Column(name = "ART_NAME", nullable = false, length = 256)
	private String name;

	@Column(name = "ART_UNIT_COST", nullable = false, columnDefinition = "Decimal(10,2)")
	private Double unitCost;

	@Column(name = "ART_STOCK", nullable = false)
	private Integer stock;

	public Article() {};
	
	public Article(String Id, String name, Double cost, Integer stock) {
		this.id = Id;
		this.name = name;
		this.unitCost = cost;
		this.stock = stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}
