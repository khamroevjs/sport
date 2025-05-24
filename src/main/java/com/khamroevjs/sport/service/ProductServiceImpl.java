package com.khamroevjs.sport.service;

import com.khamroevjs.sport.exception.ProductNotFoundException;
import com.khamroevjs.sport.dto.ProductDTO;
import com.khamroevjs.sport.model.Product;
import com.khamroevjs.sport.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.name(), productDTO.price());
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    @Override
    public Product updateProduct(int id, ProductDTO productDTO) {
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
        oldProduct.setName(productDTO.name());
        oldProduct.setPrice(productDTO.price());
        productRepository.save(oldProduct);
        return oldProduct;
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
