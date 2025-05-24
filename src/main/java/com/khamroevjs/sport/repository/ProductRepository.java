package com.khamroevjs.sport.repository;

import com.khamroevjs.sport.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

