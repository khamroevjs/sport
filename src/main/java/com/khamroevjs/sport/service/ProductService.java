package com.khamroevjs.sport.service;

import com.khamroevjs.sport.dto.ProductDTO;
import com.khamroevjs.sport.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product createProduct(ProductDTO productDTO);

    Page<Product> getAllProducts(Pageable pageable);

    Product getProduct(int id);

    Product updateProduct(int id, ProductDTO productDTO);

    void deleteProduct(int id);
}
