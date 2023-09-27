package com.dbaotrung.example.coffee.store.controller;

import com.dbaotrung.example.coffee.store.dto.GeneralApiResponse;
import com.dbaotrung.example.coffee.store.dto.PageDto;
import com.dbaotrung.example.coffee.store.entity.CoffeeStore;
import com.dbaotrung.example.coffee.store.entity.Product;
import com.dbaotrung.example.coffee.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/")
    public PageDto<Product> loadAllProducts(@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                            @RequestParam(value = "orders", required = false, defaultValue = "[]")
                              List<Sort.Order> orders) {
        return PageDto.createSuccessResponse(productService.loadAllProducts(pageSize, pageIndex, orders));
    }

    @PostMapping(value = "/create-or-update")
    public GeneralApiResponse<Product> createOrUpdateProduct(@RequestBody Product product) {
        return new GeneralApiResponse<>(productService.createOrUpdateProduct(product));
    }

    @DeleteMapping(value = "/{productId}")
    public GeneralApiResponse<Boolean> deleteProduct(@PathVariable("productId") long entityId) {
        return new GeneralApiResponse<>(productService.deleteProduct(entityId));
    }

    @GetMapping(value = "/{productId}")
    public GeneralApiResponse<Product> viewDetails(@PathVariable("productId") long entityId) {
        return new GeneralApiResponse<>(productService.loadProductDetails(entityId));
    }
}
