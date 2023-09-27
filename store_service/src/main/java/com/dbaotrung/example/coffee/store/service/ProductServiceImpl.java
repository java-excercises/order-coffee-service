package com.dbaotrung.example.coffee.store.service;

import com.dbaotrung.example.coffee.store.entity.Product;
import com.dbaotrung.example.coffee.store.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> loadAllProducts(int pageSize, int pageIndex, List<Sort.Order> orders) {
        log.info("Search entities");
        if (pageSize > 0 && pageIndex >= 0) {
            PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(orders));
            log.info("Load by page {} and page index {} without search text.", pageSize, pageIndex);
            return productRepository.findAll(pageRequest);
        } else {
            Sort sort = Sort.by(orders);
            log.info("Load without search text and order {}", orders);
            List<Product> entities = productRepository.findAll(sort);
            return new PageImpl<>(entities);
        }
    }

    @Override
    public Product createOrUpdateProduct(Product product) {
        log.info("Entering createOrUpdateEntity...");
        Product entity;
        if (product.getId() != null) {
            log.info("CoffeeStore ID not empty! Checking if existed in DB or not...");
            Optional<Product> optional = productRepository.findById(product.getId());
            if (optional.isPresent()) {
                log.info("CoffeeStore found!");
                entity = optional.get();
            } else {
                log.info("CoffeeStore not found! Creating new CoffeeStore...");
                entity = new Product();
            }
        } else {
            log.info("Entity ID empty! Creating new entity...");
            entity = new Product();
        }
        // Copy matched properties
        log.info("Copying matched properties from DTO to entity...");
        entity.setOriginalPrice(product.getOriginalPrice());
        entity.setName(product.getName());
        log.info("Save entity and response");
        return productRepository.save(entity);
    }

    @Override
    public boolean deleteProduct(long itemId) {
        Optional<Product> optional = productRepository.findById(itemId);
        if (optional.isPresent()) {
            Product item = optional.get();
            productRepository.delete(item);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Product loadProductDetails(long itemId) {
        return productRepository.findById(itemId).get();
    }
}
