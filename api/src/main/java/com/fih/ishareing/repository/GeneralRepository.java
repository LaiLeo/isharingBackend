package com.fih.ishareing.repository;

import java.io.Serializable;
import java.util.List;


public interface GeneralRepository<T, ID extends Serializable> {
	T findByIdAndActive(ID id, boolean active);
	List<T> findByActive(boolean active);
	long countByActive(boolean active);
}
