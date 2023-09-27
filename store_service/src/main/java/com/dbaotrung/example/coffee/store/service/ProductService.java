package com.dbaotrung.example.coffee.store.service;

import com.dbaotrung.example.coffee.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {

    Page<Product> loadAllProducts(int pageSize, int pageIndex, List<Sort.Order> orders);

    Product createOrUpdateProduct(Product product);

    boolean deleteProduct(long itemId);

    Product loadProductDetails(long itemId);
}
