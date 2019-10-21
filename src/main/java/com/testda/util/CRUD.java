package com.testda.util;

import java.util.List;

public interface CRUD<T> {

	List<T> findAll();

	T create(T obj);

	T findById(T id);

	T update(T obj);

	void delete(String id);
}
