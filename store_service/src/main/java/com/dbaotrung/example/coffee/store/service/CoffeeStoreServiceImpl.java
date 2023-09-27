package com.dbaotrung.example.coffee.store.service;

import com.dbaotrung.example.coffee.store.dto.MenuItemDto;
import com.dbaotrung.example.coffee.store.dto.ProductDto;
import com.dbaotrung.example.coffee.store.entity.CoffeeStore;
import com.dbaotrung.example.coffee.store.entity.MenuItem;
import com.dbaotrung.example.coffee.store.ex.ServiceHandlingException;
import com.dbaotrung.example.coffee.store.repository.CoffeeStoreRepository;
import com.dbaotrung.example.coffee.store.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class CoffeeStoreServiceImpl implements CoffeeStoreService {

    @Autowired
    private CoffeeStoreRepository coffeeStoreRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<CoffeeStore> loadAllStores(int pageSize, int pageIndex, List<Sort.Order> orders) {
        log.info("Search entities");
        if (pageSize > 0 && pageIndex >= 0) {
            PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(orders));
            log.info("Load by page {} and page index {} without search text.", pageSize, pageIndex);
            return coffeeStoreRepository.findAll(pageRequest);
        } else {
            Sort sort = Sort.by(orders);
            log.info("Load without search text and order {}", orders);
            List<CoffeeStore> entities = coffeeStoreRepository.findAll(sort);
            return new PageImpl<>(entities);
        }
    }

    @Override
    public CoffeeStore createOrUpdateStore(CoffeeStore coffeeStore) {
        log.info("Entering createOrUpdateEntity...");
        CoffeeStore entity;
        if (coffeeStore.getId() != null) {
            log.info("CoffeeStore ID not empty! Checking if existed in DB or not...");
            Optional<CoffeeStore> optional = coffeeStoreRepository.findById(coffeeStore.getId());
            if (optional.isPresent()) {
                log.info("CoffeeStore found!");
                entity = optional.get();
            } else {
                log.info("CoffeeStore not found! Creating new CoffeeStore...");
                entity = new CoffeeStore();
            }
        } else {
            log.info("Entity ID empty! Creating new entity...");
            entity = new CoffeeStore();
        }
        // Copy matched properties
        log.info("Copying matched properties from DTO to entity...");
        entity.setStoreLocation(coffeeStore.getStoreLocation());
        entity.setName(coffeeStore.getName());
        entity.setMaxNoQueues(coffeeStore.getMaxNoQueues());
        log.info("Save entity and response");
        return coffeeStoreRepository.save(entity);
    }

    @Override
    public boolean deleteStore(long itemId) {
        Optional<CoffeeStore> optional = coffeeStoreRepository.findById(itemId);
        if (optional.isPresent()) {
            CoffeeStore item = optional.get();
            coffeeStoreRepository.delete(item);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CoffeeStore loadStoreDetails(long itemId) {
        return coffeeStoreRepository.findById(itemId).get();
    }

    @Override
    public CoffeeStore updateStoreMenu(long storeId, List<MenuItemDto> menuItemDtos) {
        var storeOptional = coffeeStoreRepository.findById(storeId);
        if (storeOptional.isEmpty()) {
            throw new ServiceHandlingException("store_not_found");
        }
        var coffeeStore = storeOptional.get();
        List<MenuItem> menuItems = new ArrayList<>(menuItemDtos.size());
        for(var dto : menuItemDtos) {
            var menuItem = new MenuItem();
            menuItem.setQuantity(dto.getQuantity());
            menuItem.setSellingPrice(dto.getSellingPrice());
            var productOptional = productRepository.findById(dto.getProductId());
            if (productOptional.isEmpty()) {
                throw new ServiceHandlingException("product_not_found");
            }
            var product = productOptional.get();
            var productDto = new ProductDto(product.getId(), product.getName(), product.getOriginalPrice());
            menuItem.setProduct(productDto);
        }
        return coffeeStore;
    }
}
