package com.argo.assessmentspring.services;

import com.argo.assessmentspring.models.Product;
import com.argo.assessmentspring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteProduct(Long id) {
        repository.deleteById(id);
        return "Product removed !! " + id;
    }

    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getId()).orElse(null);
        if(existingProduct == null){
            return null;
        }
        existingProduct.setName(product.getName());
        existingProduct.setUnitPrice(product.getUnitPrice());
        existingProduct.setSkuCode(product.getSkuCode());
        return repository.save(existingProduct);
    }
}
